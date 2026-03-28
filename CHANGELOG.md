# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/), and this project adheres to [Semantic Versioning](https://semver.org/).

## [0.14.0] - 2026-03-28

### Changed

- Migrated to fluid-gradle 3.0.0 (Kotlin 2.3.20, Gradle 9.4.1, JDK 21+).
- Updated CLDR data from version 37 to 48.
- Updated fluid-country dependency from 0.13.0 to 0.14.0.
- Updated fluid-currency dependency from 0.13.0 to 0.14.0.
- Updated fluid-locale dependency from 0.13.0 to 0.14.0.
- Updated fluid-cldr dependency from 0.9.3-37 to 0.10.0-48.
- Updated KotlinPoet dependency from 1.14.2 to 1.18.0.

### Removed

- Darwin/Native target support (iOS, macOS, tvOS, watchOS).

### Deprecated

- JS target support (will be removed in a future version).
