

# Rent-a-Car

## User guide to make the project run


### **1. Add the following `Environment Variables` with your own data:**

- To connect to the database you have to add the following variables:
> DB_URL  - the URL address to your database  
> DB_USERNAME  - the username used for the database  
> DB_PASSWORD  - the password used for to connect to the database  
> DB_CREATION_TYPE - to determine the database strategy  

- To generate JSON Web Token: 
> secret_key=${SECRET_JWT_KEY}

- To generate hash from plain text password:
> backoffice.pbkdf2.secret_key=${PBKDF2_SECRET_KEY}

### **2. Docker container**

**Prerequisites:**
-   Docker and Docker Compose must be installed on your system. If not installed, you can download and install Docker Desktop from the official Docker website: [Docker Desktop](https://www.docker.com/products/docker-desktop).

    ### **Step 1: Create new file and replace the data**

    Make a new file in the same directory where are the other Docker files, with the name `docker-compose.override.yaml` in which you copy the content of the `docker-compose.override.sample.yaml` and change the data with your own data.

    ### **Step 2: Navigate to Your Project Directory**

    Open a terminal or command prompt and navigate to the directory where your `docker-compose.yaml` file is located.

        `cd /path/to/your/project`

    ### **Step 3: Start Docker Compose Services**

    Run the following command to start the services defined in your `docker-compose.yaml` file.

        `docker-compose up -d`

    The `-d` flag runs the services in the background, allowing you to continue using the terminal.

    ### Step 4: Stopping Docker Compose Services

    When you're finished, stop the services using the following command:

        `docker-compose down`

    This stops and removes the containers defined in your `docker-compose.yaml` and `docker-compose.override.yaml` files.
