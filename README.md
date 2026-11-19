# App X-Ray — starter Android MVP

This starter app inventories installed Android packages and lists permissions each package declares. The scan is local and the app has no Internet permission.

## Build an APK with GitHub Actions
1. Extract this ZIP.
2. Open https://github.com/shansclensky/Xray-apk
3. Use **Add file → Upload files** and upload the contents of this folder to the repository root on `main`, including the hidden `.github` folder.
4. Commit the upload. The workflow should start automatically.
5. Open **Actions** → **Build App X-Ray APK** → the latest successful run → download `app-xray-debug-apk`.
6. Extract that artifact ZIP. It contains `app-debug.apk`. Transfer it to your Android phone and install it.

## Scope
This MVP is an app inventory and declared-permission viewer, not a malware detector. It does not yet inspect APK contents, detect trackers, assign risk scores, or include the optional VPN monitor. `QUERY_ALL_PACKAGES` is used for personal testing; Google Play restricts broad package visibility and may require policy approval. The workflow builds a debug APK for sideloading, not a Play Store release.
