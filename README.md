# Puma

Puma is an already-jailbroken-iOS toolkit written in Kotlin Native.

## Usage

### Setup

- Jailbroken iOS Device (Tested on iPhone 5 with h3lix and iPhone 6s with checkra1n)
- Install `ldid` via Cydia on device (for signing executables)
  - Cydia Latest: `apt-get install ldid`
- Enable SSH on iOS Device
- Copy SSH key to device `ssh-copy-id root@MY_IPHONE_SSH_HOST`

### Deploy to iOS ARM64 Device

```bash
# Installs executable to /usr/bin/puma.kexe
PUMA_DEVICE_HOST=MY_IPHONE_SSH_HOST ./gradlew deployIosArm64
```

### Deploy to iOS ARM32 Device

```bash
# Installs executable to /usr/bin/puma.kexe
PUMA_DEVICE_HOST=MY_IPHONE_SSH_HOST ./gradlew deployIosArm32
```

### Execute on Device

```bash
$ /usr/bin/puma.kexe
Usage: puma <command> [args]

Commands:
  urlsession-get <url>: Fetch content of a URL
  launch <application>: Launch an Application by Identifier
  device-info: Show Device Information
  vibrate: Play Vibration Alert
```
