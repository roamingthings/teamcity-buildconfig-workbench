# teamcity-buildconfig-workbench

This is a smiple project to experiment with the TeamCity _Portable Kotlin DSL_.

It provides all required services in containers that can be run
using docker-compose.

The Services included are:

* TeamCity with one agent as CI/CD tool
* Gogs to manage git repositories
* Mailhog to receive mails from TeamCity or Gogs

## ... Why?

I wanted to experiment with the _Portable Kotlin DSL_ in an isolated
environment.

### [MailHog](https://github.com/mailhog/MailHog)

A simple mail server with web ui and API.

### [Gogs (Git-Server)](https://gogs.io/)

Leightweight UI  to manage git repositories.

### [TeamCity (CI/CD)](https://www.jetbrains.com/teamcity/)

This server can be used for free as long as less than 100 Build configurations (jobs) are defined and no more than 3 agents are used. 
See [JetBrains License Policy](https://confluence.jetbrains.com/display/TCD18/Licensing+Policy) for more details.

## Setup

Run the `init.sh` script in the `docker` directory. This will create the required keys in the `docker/keys` folder.

Then just run `docker-compose up` (you may have to do a sudo).  To run
everything in the background, use the ```-d``` option.

You will find more information about docker-compose at: https://docs.docker.com/compose/install/#install-compose

### Setup Gogs

On first run Gogs will ask you to complete the installation.

Select the following options:
* Select the SQLite3 option
* Replace `localhost` with `gogs` on the Domain and Application URL
* Email Service Settings
  * `mailhog:1025`
  * `gogs@tcworkbench.com`
* Server and Other Services Settings
  * Select _Enable Mail Notifications_
  * Leave the other options disabled
* Select OK
* Log in and create an account via "Sign up now". The first account will be granted admin privileges by default.
* Add the **public** ssh key from the `docker/keys` folder to the SSH-Keys of the user.

### Setup TeamCity

* Click Proceed
* Select HSQLDB (local database)
* Accept License and disable anonoymous stats
* Create the Admin account
  * Choose a username and password
  * teamcity@tcworkbench.com
* Go to _Administration -> Email Notifier_
  * Enter `mailhog` for the server
  * Set `1025` as port
  * Test Connection. You can choose any email address as the recipient.
* Add the Agent
  * Select Agents -> Unauthorized (there should be 1)
  * Select the Agent named `regular_agent`
  * Click _Authorize Agent_
* Setup the ssh keys
  * Select the _Root Project_ for example in _Administration -> Projects_
  * Select _SSH Keys_ on the left-hand menu
  * Pick Upload SSH Keys and upload the _private_ key from the `docker/keys` folder.

## Accessing the repository

Depending on where you want to access the repository you need different
URLs for the repository.

To access the repository from TeamCity use the following URL:
```
ssh://git@gogs:22/<user>/teamcity-buildconfig-workbench.git
```

The same repository can be accessed from the outside using:
```
ssh://git@localhost:10022/<user>/teamcity-buildconfig-workbench.git
```

It seems to be important to explicitly add the port to the url. Otherwise
you may get an error claiming that no key can be found.

Be sure to have the private key from the `docker/keys` folder available
to your ssh client when operating with the repository from your machine.
Alternatively you can add your default public key to the Gogs account.

## FinishBuildTriggers

* If this project is a subproject of ParentProject1Id add
finishBuildTriggers that reference build steps from
two other projects (AnotherProject1Id, AnotherProject2Id)
* If this project is a subproject of ParentProject2Id add a
finishBuildTrigger that reference build steps from
one other project (AnotherProject3Id)
* In any other case don't add any finishBuildTrigger at all

```
 /
 +- AnotherProject1Id
 |  |
 |  \- AnotherProject1Id_triggerBuildType
 |
 +- AnotherProject2Id
 |  |
 |  \- AnotherProject2Id_triggerBuildType
 |
 +- AnotherProject3Id
 |  |
 |  \- AnotherProject3Id_triggerBuildType
 |
 +- ParentProject1Id
 |  |
 |  \- ParentProject1Id_ThisProjectId_Build (Trigger after AnotherProject1Id, AnotherProject2Id)
 |
 +- ParentProject2Id
 |  |
 |  \- ParentProject2Id_ThisProjectId_Build (Trigger after AnotherProject3Id)
 |
 +- ParentProject3Id
    |
    \- ParentProject3Id_ThisProjectId_Build (no finshBuildTrigger)
```
