[![Github](https://img.shields.io/github/followers/aldefy?label=Follow&style=social)](https://github.com/aldefy)
[![Twitter](https://img.shields.io/twitter/follow/aditlal?label=Follow&style=social)](https://twitter.com/aditlal)
[![Build and Deploy](https://github.com/aldefy/Andromeda/actions/workflows/build_deploy.yml/badge.svg)](https://github.com/aldefy/Andromeda/actions/workflows/build_deploy.yml)


[![AndromedaVersion](https://img.shields.io/maven-central/v/design/andromedacompose)](https://search.maven.org/artifact/design/andromedacompose)

<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/aldefy/Andromeda">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>
  <h3 align="center">Andromeda</h3>

  <p align="center">
    Andromeda is a open-source design language system implemented in Jetpack Compose.
    <br />
    <a href="https://play.google.com/store/apps/details?id=design.andromedacompose.catalog">Catalog app</a>
  </p>
</p>

<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Usage](#usage)
* [Releases](#releases)
* [Roadmap](#roadmap)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)

<!-- ABOUT THE PROJECT -->
## About The Project

### Andromeda 
Welcome 👋 Andromeda is an open-source Jetpack Compose design system. A collection of guidelines and components which can be used to create amazing compose app user experiences. `Foundations` introduces you to Andromeda tokens and principles while `Components` lists out the bolts and nuts that make Andromeda Compose wrapped apps tick.

#### Catalog app
![6M9n8EkDLLpU_1024_500](https://user-images.githubusercontent.com/1392571/153181371-5b163288-8dc5-430a-aad7-051741f36070.png)


<!-- GETTING STARTED -->
## Getting Started

In `build.gradle` of your application module, include this dependency

```gradle
dependencies {
  implementation("design.andromedacompose:$AndromedaVersion")
}
```

If you want to use our provided Icons, also add

```gradle
dependencies {
    implementation("design.andromedacompose-icon:$AndromedaVersion")
}
```


If you want to use our provided Illustrations, also add

```gradle
dependencies {
    implementation("design.andromedacompose-illustrations:$AndromedaVersion")
}
```

### Prerequisites
- Android Studio Bumblee
- Java 11

<!-- Usage -->
## Usage
### Theme
```kotlin
AndromedaTheme {
 // Your compose content
}
```

or 
create an extension theme for your app with `custom` attributes such as colors, font etc

```kotlin 
@Composable
fun CatalogTheme(
    isLightTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    AndromedaTheme(
        colors = if (isLightTheme) defaultLightColors() else defaultDarkColors(),
    ) {
        content()
    }
}
```
Ref : [CatalogTheme.kt](https://github.com/aldefy/Andromeda/blob/main/catalog/src/main/java/design/andromedacompose/catalog/CatalogTheme.kt)

### Foundation
#### Colors
```kotlin
fun myCustomAndromedaColors(
    primaryColors: PrimaryColors = customPrimaryLightColors(),
    secondaryColors: SecondaryColors = customSecondaryLightColors(),
    tertiaryColors: TertiaryColors = customTertiaryLightColors(),
    borderColors: BorderColors = customBorderLightColors(),
    iconColors: IconColors = customIconsLightColors(),
    contentColors: ContentColors = customContentLightColors()
): AndromedaColors = AndromedaColors(
    primaryColors = primaryColors,
    secondaryColors = secondaryColors,
    tertiaryColors = tertiaryColors,
    borderColors = borderColors,
    iconColors = iconColors,
    contentColors = contentColors,
    isDark = false
)
```
Colors can be broken down into : 
- Primary colors
- Secondary colors
- Tertiary colors
- Border colors
- Icons colors
- Content colors 

and also `AndromedaTheme` supports dark/light modes - one can override it for a given screen or entire app by passing `true` in `AndromedaColors.isDark`

For more details on `Colors` - check out Documenation [here](https://aldefy.github.io/Andromeda/andromeda/design.andromedacompose.foundation.colors/index.html)

#### Shapes
The library provides the following contract to extend on : 
```kotlin
interface BasicShapes {
    val small: CornerBasedShape
    val normal: CornerBasedShape
    val large: CornerBasedShape
}

interface AndromedaShapes : BasicShapes {
    val bottomSheet: Shape
    val buttonShape: Shape
    val dialogShape: Shape
}
```

with some default shapes : 
```kotlin

/**
 * Contains default shapes this library provides for components.
 *
 * @param bottomSheet - The shape of components used as bottom sheets.
 * @param buttonShape - The shape of components used as buttons.
 * @param dialogShape - The shape of components used for showing dialog box.
 * */
DefaultShapes(
    override val bottomSheet: Shape,
    override val buttonShape: Shape,
    override val dialogShape: Shape,
    override val small: CornerBasedShape,
    override val normal: CornerBasedShape,
    override val large: CornerBasedShape,
) : AndromedaShapes
```
One can extend further or generate cusotm shapes on the fly , for more documentation on shapes go [here](https://aldefy.github.io/Andromeda/andromeda/design.andromedacompose.foundation.shape/index.html)

#### Typography
`AndromedaTheme` provides custom typography for common use cases
Typography has default fonts shipped in `andromeda` artifact - you can also override your own in your app/root module - example of using custom fonts can be found in [Catalog app](https://github.com/aldefy/Andromeda/blob/main/catalog/src/main/java/design/andromedacompose/catalog/CatalogTheme.kt#L18)

Typography has following breakdown: 
```kotlin
    val titleHeroTextStyle: TextStyle,
    val titleModerateBoldTextStyle: TextStyle,
    val titleModerateDemiTextStyle: TextStyle,
    val titleSmallDemiTextStyle: TextStyle,
    val bodyModerateDefaultTypographyStyle: TextStyle,
    val bodySmallDefaultTypographyStyle: TextStyle,
    val captionModerateBookDefaultTypographyStyle: TextStyle,
    val captionModerateDemiDefaultTypographyStyle: TextStyle
```

To see them in action , checkout the [Catalog app](https://play.google.com/store/apps/details?id=design.andromedacompose.catalog)

### Components - WIP

#### Buttons
#### NavBar
#### Icon
#### Divider
#### Circular Reveal
#### Text
#### Surface
#### BackButton

<!-- Releases -->
## Releases
* 1.0.0-alpha01
    * Initial release with a catalog app showcasing icons, illustrations and other components alongside Foundational toekns of `AndromedaTheme`


<!-- ROADMAP -->
## Roadmap
- Multi platform support apart from just Android, desktop , iOS , KMM in near future.

See the [open issues](/Issues.md) for a list of proposed features (and known issues).

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are**greatly appreciated**.


1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<!-- LICENSE -->
## License

Distributed under the MIT License. See`LICENSE`for more information.

<!-- CONTACT -->
## Contact

Adit Lal - [@aditlal](https://twitter.com/aditlal) - https://aditlal.dev

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/confluxtoo/loanbook-app.svg?style=flat-square
[contributors-url]: https://github.com/aldefy/Andromeda/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/confluxtoo/loanbook-app.svg?style=flat-square
[forks-url]: https://github.com/aldefy/Andromeda/network/members
[stars-shield]: https://img.shields.io/github/stars/confluxtoo/loanbook-app.svg?style=flat-square
[stars-url]: https://github.com/aldefy/Andromeda/stargazers
[issues-shield]: https://img.shields.io/github/issues/confluxtoo/loanbook-app.svg?style=flat-square
[issues-url]: https://github.com/aldefy/Andromeda/issues
[license-shield]: https://img.shields.io/github/license/confluxtoo/loanbook-app.svg?style=flat-square
[license-url]: https://github.com/aldefy/Andromeda/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/aditlal
