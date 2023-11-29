FROM openjdk:21
#https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-container-registry#labelling-container-images
#TODO replace "Template" with the name of the project
LABEL org.opencontainers.image.source=https://github.com/trinext/Template
#TODO replace "Template" with the name of the project
LABEL org.opencontainers.image.description="Template Container image"
#TODO replace "Template" with the name of the project
COPY ./build/libs/Template-fat.jar Template.jar
#TODO replace "Template" with the name of the project
ENTRYPOINT ["java","-jar","/Template.jar"]