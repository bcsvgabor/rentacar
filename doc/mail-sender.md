<img src="https://i.ibb.co/zNrpL1b/images.png" alt="images" border="0">

# Fox Back Office

## User guide to send an email via SendGrid email-provider


### **1. Set up your SendGrid profile :**

- Create an account at https://sendgrid.com/en-us
- At the `Settings/API Keys` create a new API Key (give name for API Key and for our use we choose the `Restricted Access`. Activate the Mail Send/ `Mail send` option)
- Save the `API Key` into your `Environment Variables`
- Go to the Settings/Sender Authentication and Verify a Single Sender (you have to verify that email adress what you will use for sending)

### **2. Create SendGrid dynamic template :**

- In your SendGrid profile go to `Email API / Dynamic templates` and create a new template
- Click on the template and `Add Version`
- In the designer tool you can create your own template or choose a pre-made template
- In your own template you can use blocks to build your template:
- At the Subject field write `{{"subject"}}`
- Create a text block and write `{{"text"}}`
- You can customize your email template as you wish and hit `Save` at the end.
- Save the `SENDGRID_TEMPLATE_ID` to the `Environment Variables`



### **3. Set up the application:**
- You can find the SendEmailTool at: `com/gfa/backoffice/utils/SendEmailTool.java`
- Create a `SENDGRID_API_KEY` environment variables with the SendGrid API Key what is provided in the SendGrid profile
- The `sendEmail()` method takes a SendEmailRequest DTO which contains all the necessarily input data
- create a `SendEmailRequest` DTO.

### **4. Run the application:**
- the application is a PoC (proof of concept) application, so to test the functionality it is enough to have a util tool and run the `SendEmailTool.main()`


