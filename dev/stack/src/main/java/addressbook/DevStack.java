package addressbook;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.docdb.ClusterParameterGroup;
import software.amazon.awscdk.services.docdb.DatabaseCluster;
import software.amazon.awscdk.services.docdb.Login;
import software.amazon.awscdk.services.ec2.InstanceClass;
import software.amazon.awscdk.services.ec2.InstanceSize;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ec2.VpcLookupOptions;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.Secret;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.constructs.Construct;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DevStack extends Stack {

    public DevStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public DevStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        var docdbMasterUsername = "master";
        var addressBookAppImageTag = Optional.ofNullable(System.getenv("ADDRESS_BOOK_APP_IMAGE_TAG")).orElse("latest");

        var vpc = Vpc.fromLookup(
            this,
            "vpc-02",
            VpcLookupOptions.builder().vpcName("vpc-02").build()
        );

        var docdbClusterParameterGroup = ClusterParameterGroup.Builder.create(this, "no-tls")
            .dbClusterParameterGroupName("deaful-docdb50-no-tls")
            .family("docdb5.0")
            .parameters(Map.of("tls", "disabled"))
            .description("default, no TLS")
            .build();

        var docdbCluster = DatabaseCluster.Builder.create(this, "address-book-docdb-cluster")
            .masterUser(
                Login.builder()
                    .username(docdbMasterUsername)
                    .excludeCharacters("!@#$%^&*()+={}[]|\\:;\"'<>,./?`~")
                    .build()
            )
            .instanceType(InstanceType.of(InstanceClass.T3, InstanceSize.MEDIUM))
            .vpcSubnets(SubnetSelection.builder().subnetType(SubnetType.PUBLIC).build())
            .vpc(vpc)
            .parameterGroup(docdbClusterParameterGroup)
            .build();

        var ecsSecurityGroup = SecurityGroup.Builder.create(this, "address-book-ecs-fargate-security-group").vpc(vpc).build();

        var ecrRepository = Repository.fromRepositoryName(this, "address-book-ecr-repository", "address-book-02");

        var ecsService = ApplicationLoadBalancedFargateService.Builder.create(this, "address-book-ecs-fargate-service")
            .vpc(vpc)
            .securityGroups(List.of(ecsSecurityGroup))
            .cpu(1024)
            .memoryLimitMiB(2048)
            .taskImageOptions(
                ApplicationLoadBalancedTaskImageOptions.builder()
                    .image(ContainerImage.fromEcrRepository(ecrRepository, addressBookAppImageTag))
                    .environment(Map.of(
                        "SPRING_DATA_MONGODB_HOST", docdbCluster.getClusterEndpoint().getHostname(),
                        "SPRING_DATA_MONGODB_PORT", docdbCluster.getClusterEndpoint().portAsString(),
                        "SPRING_DATA_MONGODB_USERNAME", docdbMasterUsername
                    ))
                    .secrets(Map.of(
                        "SPRING_DATA_MONGODB_PASSWORD", Secret.fromSecretsManager(docdbCluster.getSecret(), "password")
                    ))
                    .containerPort(8080)
                    .build())
            .build();

        ecsService.getTargetGroup().configureHealthCheck(
            HealthCheck.builder()
                .path("/actuator/health")
                .interval(Duration.seconds(30))
                .build()
        );

        docdbCluster.getConnections().allowFrom(ecsSecurityGroup, Port.tcp(docdbCluster.getClusterEndpoint().getPort()));
    }
}
