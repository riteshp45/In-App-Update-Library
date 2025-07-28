# 🚀 In-App Update UI Library for Android

A customizable **in-app update UI library** for Android that works with Google Play’s In-App Update API.  
It shows a beautiful **circular progress animation**, update messages, and restart prompt using a flexible layout.

---

## ✨ Features

- 📦 Fully customizable circular progress bar
- 🎨 Support for dynamic colors, text, and icons
- 🔁 Restart prompt when update is ready
- 🎯 Works with both **Flexible** update types
- 🧩 Easy to integrate and lightweight
- ☕ Kotlin & Java both supported

---

## 📲 Installation

### Step 1: Add JitPack to your root `build.gradle`:

allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

### Step 2: Add the library dependency::
dependencies {
    implementation 'com.github.your-github-username:in-app-update-ui:1.0.3'
}


🛠️ Usage

✅ Initialize and Configure

AppUpdateLayoutHelper appUpdateLayoutHelper = new AppUpdateLayoutHelper(this);

appUpdateLayoutHelper.configureProgressBar(
    R.drawable.ic_app_update,
    getString(R.string.app_update_title),
    ContextCompat.getColor(this, R.color.colorPrimary),
    ContextCompat.getColor(this, R.color.blue),
    22f, // percentage text size
    14f, // progress stroke width
    16f  // background stroke width
);

🔄 Download Progress Listener


InstallStateUpdatedListener installStateUpdatedListener = state -> {
    if (state.installStatus() == InstallStatus.DOWNLOADING) {
        runOnUiThread(() -> {
            long bytesDownloaded = state.bytesDownloaded();
            long totalBytesToDownload = state.totalBytesToDownload();
            appUpdateLayoutHelper.showDownloading(bytesDownloaded, totalBytesToDownload);
        });

    } else if (state.installStatus() == InstallStatus.DOWNLOADED) {
        appUpdateLayoutHelper.showRestartApp(() -> {
            mAppUpdateManager.completeUpdate();
            appUpdateLayoutHelper.hide();
            return null;
        });

    } else if (state.installStatus() == InstallStatus.INSTALLED) {
        mAppUpdateManager.unregisterListener(installStateUpdatedListener);
    }
};


