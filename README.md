# Puma

Puma is an already-jailbroken-iOS toolkit written in Kotlin Native.

## Usage

### Deploy to iOS ARM64 Device

```bash
PUMA_DEVICE_HOST=MY_IPHONE_SSH_HOST ./gradlew deployIosArm64
```

### Execute on iOS ARM64 Device

```bash
/usr/bin/puma.kexe launch com.apple.mobilesafari
```
