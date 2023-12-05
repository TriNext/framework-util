# How to work on this Repository

## In your editor (IntelliJ)

- Go to `File > Settings > Tools > Actions on Save`
    - enable `Reformat code (Whole File)`
    - enable `Optimize imports`
    - enable `Rearrange code`

## How to publish
increment the version in the `build.gradle.kts`
set TONYS_PAT and TONYS_USERNAME in your environment
execute `./gradlew publish`