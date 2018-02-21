# UpseilGDX

A collection of projects that have been developed while working on hobby game projects based on [libGDX](https://github.com/libgdx/libgdx).
The projects are separated by the library they extend.

| Project | Description | Dependencies |
|--------:|:------------|:-------------|
|[UpseilGDX-Util](./util)| Independent utilites, for example functional interfaces for primitives or formatting utilites. | None |
|[UpseilGDX-GDX](./gdx)| General libGDX extensions, for example for Scene2D and object pooling and new utilities like serialization with jackson or Base64/LZW encoding. | UpseilGDX-Util, [ligGDX-GDX](https://github.com/libgdx/libgdx), [Jackson Databind](https://github.com/FasterXML/jackson-databind) |
|[UpseilGDX-GWT](./gwt)| Extensions for projects targeting web, mainly serializers for [gwt-jackson](https://github.com/nmorel/gwt-jackson). | UpseilGDX-GDX, [gwt-jackson](https://github.com/nmorel/gwt-jackson) |
|[UpseilGDX-Artemis](./artemis)| Extensions for projects using [artemis-odb](https://github.com/junkdog/artemis-odb) as ECS, mainly general purpose systems and special invocation strategies. | UpseilGDX-GDX, [artemis-odb](https://github.com/junkdog/artemis-odb) |
|[UpseilGDX-Box2D](./box2d)| Extensions for projects using [artemis-odb](https://github.com/junkdog/artemis-odb) as ECS and [Box2D](https://github.com/erincatto/Box2D) for physics, mainly a physics system that integrates Box2D in an artemis world and (a more or less profound) fluent builder API for Box2D bodies. | UpseilGDX-GDX, UpseilGDX-Artemis, [ligGDX-Box2D](https://github.com/libgdx/libgdx) |

Projects containing tests also depend on [JUnit4](https://junit.org/junit4/) and [Java Hamcrest](https://github.com/hamcrest/JavaHamcrest).

# Usage

These projects are mainly a result from extracting general functionalities from my hobby game projects and are far from finished/solid, nearly undocumented and with very few tests. I'm actively working on every part and breaking changes can happen anytime and anywhere. This is also the reason why the projects aren't on maven or other distribution platforms. If you still want to risk using the extension in your own projects follow these steps (requires gradle as build tool):

1. Clone this repository or add it as submodule to your project
2. Add `apply from: "<relative path to UpseilGDX>/upseilgdx-import.gradle"` to your `settings.gradle` (see [upseilgdx-import.gradle](./upseilgdx-import.gradle))
3. Adjust the top level `build.gradle` file of your project
	1. Specify the necessary version variables (e.g. `gdxTarget` or `artemisTarget`)
	2. Specify the relative path to the UpseilGDX location with a variable named `upseilGdxPath` (e.g. `../libs/UpseilGDX`)
	3. Add `apply from: "$upseilGdxPath/upseilgdx-setup.gradle"` anywhere after the variable declarations (see [upseilgdx-setup.gradle](./upseilgdx-setup.gradle))
4. Add the corresponding dependencies to your project specific `build.gradle` files (e.g. `compile project(":upseilgdx:gdx")`)

See https://github.com/Upseil/Template for a nearly empty example project that is configured with a reference to UpseilGDX. The black gradle magic is done in the subdirectory `Game`. If you decide to clone the template or use it as a starting point, don't forget to initialize and update the UpseilGDX submodule (e.g. with `git submodule update --init`).

# License

All projects are licensed under the [MIT License](./LICENSE).
