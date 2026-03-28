# fluid-i18n

Kotlin multiplatform internationalization library backed by Unicode CLDR data.

## Build

```shell
./gradlew build
./gradlew allTests
```

## Code generation

Generated files live in `modules/data-identifiers/sources/` and `modules/data-regions/sources/`. Regenerate from CLDR data:

```shell
./gradlew generateCode
```

After Kotlin version bumps, regenerate the JS lock file:

```shell
./gradlew kotlinUpgradeYarnLock
```

## Conventions

- Tags have no `v` prefix (e.g. `0.14.0`)
- `kotlin-js-store/yarn.lock` must be committed for reproducible JS builds
- Generated `*.generated.kt` files are committed — do not edit manually
