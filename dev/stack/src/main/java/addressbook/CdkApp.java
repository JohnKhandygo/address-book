package addressbook;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class CdkApp {

    public static void main(final String[] args) {
        App app = new App();

        new DevStack(app, "DevStack", StackProps.builder()
            .env(Environment.builder()
                .account(System.getenv("CDK_ACCOUNT"))
                .region(System.getenv("CDK_REGION"))
                .build())
            .build());

        app.synth();
    }
}

