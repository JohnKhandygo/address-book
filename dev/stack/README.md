This is a CDK app for address-book app deployment.

## How to run it

Assuming, your AWS account is already CDK-enabled (i.e. `cdk bootstrap` has been run):

1. Ensure that your AWS principal has the following or equivalent policy:
     ```json
     {
       "Version": "2012-10-17",
       "Statement": [
         {
           "Effect": "Allow",
           "Action": [
             "sts:AssumeRole"
           ],
           "Resource": [
             "arn:aws:iam::*:role/cdk-*"
           ]
         }
       ]
     }
     ```
2. To deploy run
   ```shell
   AWS_PROFILE=<profile_name> \
   CDK_ACCOUNT=$(aws sts get-caller-identity --profile <profile_name> | jq -r .Account) \
   CDK_REGION=<region> \
   ADDRESS_BOOK_APP_IMAGE_TAG=<image_tag> \ 
   cdk deploy
   ```
   > The above command relies on `jq` to extract AWS account number from the current identity.
   
   > If `ADDRESS_BOOK_APP_IMAGE_TAG` is not specified, an image with the `latest` tag will be deployed.
3. To undeploy run the same command with `destroy` in the end instead of `deploy`.
   > Destroying the app will retain the DocumentDB cluster and all the data with it.
