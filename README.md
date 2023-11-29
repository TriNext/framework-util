# How to work on this Repository
- All settings that depended on whether the project will be an application or a library need to be commented out. The file these settings are in needs to be listed under "Application vs Library" in this README.md.

# How to configure?

See the associated [Confluence](https://trinext.atlassian.net/wiki/spaces/JTK/pages/115933200/Erstellen+von+neuen+Projekten)-Documentation.

## Application vs Library

Check the following files for the right settings for your type of Project:

- `build.gradle.kts`

Delete the whole ``docker`` directory if the project is a library or will not be deployed

Delete the openapi.yaml if the project won't have an API.

## In your Project
- Replace all occurences of "TemplateRepository in .idea/* with the name of your repository"
- Check Jenkinsfile.groovy
  - add the teams webhook url
  - projectName
- Add the project name
    - in the `settings.gradle.kts` => `rootProject.name`
    - Dockerfile
    - compose.yaml
    - main-compose.settings
    - staging-compose.settings
    - .idea/.name
- replace $PROJEKTNAME in postman/* with project name
- Add the Repository URL
    - in the `Jenkinsfile.groovy`
- Activate GitHub Actions
    - in `pr-validations.yml`
- Dockerfile
- add the right ports
    - main-compose.settings
    - staging-compose.settings

## In your editor (IntelliJ)

- Go to `File > Settings > Tools > Actions on Save`
    - enable `Reformat code (Whole File)`
    - enable `Optimize imports`
    - enable `Rearrange code`

# How to start

delete this file if you are done with the configuration and write a useful readme.md

If your Project will have an API start by creating the API-Endpoints in the `openapi.yaml` file.