# VitaMind — BLE Android App for Stress Monitoring (VitaminD prototype)

> **TL;DR** — An Android app (Kotlin) that connects over **Bluetooth Low Energy** to a wearable/sensor
> device to capture biometric signals for **VitaminD**, a wellbeing prototype focused on stress
> monitoring. It handles the full BLE lifecycle (scan → connect → discover services → read/notify) so
> physiological data can be streamed to the phone for analysis and feedback.
>
> **Stack:** Kotlin · Android (min SDK 21, target 34) · Bluetooth Low Energy · Gradle.
> **Base:** built on Punch Through's open BLE starter, extended for the VitaminD use case.

---

## What it does
- Scans for and connects to a nearby BLE sensor (e.g. heart-rate / biosignal wearable).
- Discovers services & characteristics, requests an ATT MTU update, and reads/subscribes to
  notifications to stream sensor data in real time.
- Feeds that data into the VitaminD stress-monitoring flow (hardware sensor + app + professional-support
  platform) — this repo is the **mobile/BLE layer** of that prototype.

## BLE capabilities implemented
Scanning · connecting · service/characteristic discovery · MTU negotiation · read/write on
characteristics & descriptors · enabling notifications/indications · bonding · a serial queue for
reliable BLE operations.

## Setup
```bash
git clone https://github.com/YeisonDelgado/VitaMind_App2.git
# Open in Android Studio, let Gradle sync, run on a device with Bluetooth (BLE needs real hardware)
```
Requires an Android device (BLE does not work on most emulators) and location/Bluetooth permissions.

## Context & next steps
- Part of the **VitaminD** university prototype (IoT sensing + wellbeing). This repo is the Android/BLE
  client; the sensor firmware and analysis live alongside it.
- Next: add on-device signal buffering, a charting screen for live biosignals, and a clean data-sync
  API to the backend.

> Note: this project builds on the open-source Punch Through "Android BLE Starter"; the BLE plumbing is
> reused and adapted for VitaminD's stress-monitoring use case.
