This is a Terraform module for foundational infrastructure provisioning.

## How to run it

1. First, [install Terraform](https://developer.hashicorp.com/terraform/tutorials/aws-get-started/install-cli).
2. Run `terraform init` to download dependencies.
3. Ensure that your AWS principal has the following or equivalent policies:
    - AWS managed [`AmazonVPCFullAccess`](https://docs.aws.amazon.com/aws-managed-policy/latest/reference/AmazonVPCFullAccess.html)
    - AWS managed [`AmazonEC2ContainerRegistryFullAccess`](https://docs.aws.amazon.com/aws-managed-policy/latest/reference/AmazonEC2ContainerRegistryFullAccess.html)
    - inlined 
        ```json
        {
            "Version": "2012-10-17",
            "Statement": [
                {
                    "Sid": "VisualEditor0",
                    "Effect": "Allow",
                    "Action": "ec2:DescribeAvailabilityZones",
                    "Resource": "*"
                }
            ]
        }
        ```
4. To provision run `AWS_PROFILE=<profile> terraform apply`.
5. To deprovision run `AWS_PROFILE=<profile> terraform destroy`.