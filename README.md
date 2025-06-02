# 🔐 CryptoShield - Advanced File Protection System

<div align="center">

![CryptoShield Logo](https://img.shields.io/badge/CryptoShield-v2.0-blue?style=for-the-badge&logo=shield&logoColor=white)

[![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![JavaFX](https://img.shields.io/badge/JavaFX-17+-green?style=for-the-badge&logo=java&logoColor=white)](https://openjfx.io/)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE)
[![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20macOS%20%7C%20Linux-lightgrey?style=for-the-badge)](https://github.com/Rajibkd1/CryptoShield)

**A beautiful, modern, and secure file encryption application built with JavaFX**

[Features](#-features) • [Installation](#-installation) • [Usage](#-usage) • [Screenshots](#-screenshots) • [Contributing](#-contributing)

</div>

---

## 🌟 Overview

CryptoShield is a state-of-the-art file encryption application that combines military-grade security with an intuitive, modern user interface. Built with JavaFX, it offers seamless file and folder protection with beautiful animations and a professional design.

## ✨ Features

### 🔒 **Advanced Security**
- **AES-256 Encryption** - Military-grade security
- **SHA-256 Hashing** - Secure password processing
- **CBC Mode** - Enhanced encryption strength
- **PKCS5 Padding** - Industry standard

### 🎨 **Beautiful Interface**
- **Modern Design** - Clean and professional UI
- **Smooth Animations** - Engaging user experience
- **Gradient Backgrounds** - Eye-catching visuals
- **Responsive Layout** - Adapts to content

### 🚀 **User Experience**
- **Drag & Drop** - Easy file selection
- **Keyboard Shortcuts** - Power user features
- **Progress Indicators** - Real-time feedback
- **System Tray** - Minimize to tray

### 📁 **File Operations**
- **Individual Files** - Encrypt/decrypt single files
- **Entire Folders** - Lock/unlock complete directories
- **Batch Processing** - Handle multiple files
- **ZIP Compression** - Efficient folder storage

## 🛡️ Security Features

| Security Feature | Implementation | Strength |
|-----------------|----------------|----------|
| 🔐 **Encryption Algorithm** | AES-256-CBC | Military Grade |
| 🔑 **Key Derivation** | SHA-256 | 256-bit |
| 🛡️ **Initialization Vector** | Unique per file | Prevents patterns |
| 🔒 **Padding Scheme** | PKCS5 | Industry standard |

## 📋 Requirements

### System Requirements
- **Java**: JDK 17 or higher
- **JavaFX**: 17 or higher
- **Memory**: 512 MB RAM minimum
- **Storage**: 50 MB free space
- **OS**: Windows 10+, macOS 10.14+, Linux (Ubuntu 18.04+)

## 🚀 Installation

### Option 1: Download Release
```bash
# Download the latest release from GitHub
wget https://github.com/Rajibkd1/CryptoShield/releases/latest/cryptoshield.jar

# Run the application
java -jar cryptoshield.jar
```

### Option 2: Build from Source
```bash
# Clone the repository
git clone https://github.com/Rajibkd1/CryptoShield.git
cd CryptoShield

# Build with Maven
mvn clean compile javafx:run

# Or build with Gradle
./gradlew run
```

### Option 3: IDE Setup
```xml
<!-- Add to your pom.xml -->
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>17.0.2</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>17.0.2</version>
    </dependency>
</dependencies>
```

## 💻 Usage

### 🎯 Quick Start Guide

#### 1. **Security Verification**
- Launch CryptoShield
- Solve the math problem: `10 + 2 × 3 = 16`
- Click "Verify Access" to continue

#### 2. **Choose Operation**
- **📄 File Encryption**: For individual files
- **📁 Folder Protection**: For entire directories

#### 3. **Select & Protect**
- Browse or drag-drop your files/folders
- Enter a strong password
- Click Encrypt/Lock to secure your data

### 🔐 File Encryption

1. **Select File**: Click "Browse Files" or drag & drop
2. **Enter Password**: Use a strong, memorable password
3. **Encrypt**: Click "🔒 Encrypt File"
4. **Result**: Original file becomes `filename.ext.enc`

### 📁 Folder Protection

1. **Select Folder**: Click "📁 Folder" button
2. **Enter Password**: Choose a secure password
3. **Lock**: Click "🔒 Lock Folder"
4. **Result**: Folder becomes `foldername.zip.enc`

## ⌨️ Keyboard Shortcuts

| Shortcut | Action | Description |
|----------|--------|-------------|
| `ESC` | Exit | Close application with animation |
| `F1` | Help | Show comprehensive help dialog |
| `Enter` | Execute | Perform default action |
| `Ctrl+D` | Drag Mode | Enable drag & drop mode |

## 🎨 Screenshots

### 🔐 Security Verification Screen
*Beautiful gradient background with floating animations and modern card design*

### 🎯 Main Application Interface
*Clean, professional interface with interactive radio button cards*

### 📄 File Encryption Panel
*Intuitive file selection with drag & drop support and beautiful buttons*

## 🔧 Configuration

### Environment Variables
```bash
# Optional: Set custom temp directory
export CRYPTOSHIELD_TEMP_DIR="/path/to/temp"

# Optional: Enable debug mode
export CRYPTOSHIELD_DEBUG=true

# Optional: Set custom theme
export CRYPTOSHIELD_THEME="dark"
```

## 🛠️ Development

### Project Structure
```
CryptoShield/
├── src/
│   └── main/
│       └── java/
│           └── com/example/locker/
│               └── FileEncryptorDecryptorGUI.java
├── resources/
│   ├── images/
│   └── styles/
├── docs/
├── README.md
└── pom.xml
```

### Building
```bash
# Compile
javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml FileEncryptorDecryptorGUI.java

# Run
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml FileEncryptorDecryptorGUI
```

## 🔒 Security Best Practices

### ⚠️ Important Security Notes

- **Password Strength**: Use passwords with 12+ characters, including uppercase, lowercase, numbers, and symbols
- **Password Storage**: CryptoShield does NOT store passwords - remember them or lose access forever
- **Backup Strategy**: Always keep unencrypted backups of critical files
- **File Verification**: Verify encrypted files can be decrypted before deleting originals

## 🤝 Contributing

We welcome contributions! Here's how you can help:

### 🐛 **Bug Reports**
- Use GitHub Issues
- Include system info
- Provide reproduction steps
- Attach screenshots

### ✨ **Feature Requests**
- Describe the feature
- Explain use cases
- Consider security implications
- Provide mockups if possible

### 🔧 **Code Contributions**
- Fork the repository
- Create feature branch
- Follow coding standards
- Submit pull request

### 📚 **Documentation**
- Improve README
- Add code comments
- Create tutorials
- Update help content

### Development Setup
```bash
# Fork and clone
git clone https://github.com/Rajibkd1/CryptoShield.git
cd CryptoShield

# Create feature branch
git checkout -b feature/amazing-feature

# Make changes and commit
git commit -m "Add amazing feature"

# Push and create PR
git push origin feature/amazing-feature
```

## 📄 License

**MIT License** - See [LICENSE](LICENSE) file for details

## 🙏 Acknowledgments

- **JavaFX Team** - For the amazing UI framework
- **Oracle** - For Java platform
- **Community** - For feedback and contributions
- **Security Researchers** - For encryption best practices

## 📞 Support

### Need Help?

[![GitHub Issues](https://img.shields.io/badge/GitHub-Issues-red?style=for-the-badge&logo=github)](https://github.com/Rajibkd1/CryptoShield/issues)
[![Documentation](https://img.shields.io/badge/Read-Documentation-blue?style=for-the-badge&logo=gitbook)](https://github.com/Rajibkd1/CryptoShield/wiki)
[![Discussions](https://img.shields.io/badge/GitHub-Discussions-purple?style=for-the-badge&logo=github)](https://github.com/Rajibkd1/CryptoShield/discussions)

## 🌟 Key Features Highlight

- **🔐 Military-Grade Encryption**: AES-256 with SHA-256 key derivation
- **🎨 Modern UI**: Beautiful gradients, animations, and responsive design
- **🚀 User-Friendly**: Drag & drop, keyboard shortcuts, and intuitive interface
- **📁 Versatile**: Handle both individual files and entire folders
- **🛡️ Secure**: No password storage, secure deletion of originals
- **⚡ Fast**: Efficient encryption/decryption algorithms
- **🌍 Cross-Platform**: Works on Windows, macOS, and Linux

## 🔧 Technical Details

### Encryption Process
1. **Password Processing**: SHA-256 hash generation
2. **Key Derivation**: 256-bit AES key from password hash
3. **IV Generation**: Unique initialization vector per file
4. **Encryption**: AES-256-CBC with PKCS5 padding
5. **File Output**: Encrypted data saved with .enc extension

### Folder Protection Process
1. **Compression**: ZIP compression of entire folder
2. **Encryption**: AES-256 encryption of ZIP file
3. **Cleanup**: Secure deletion of original folder
4. **Output**: Encrypted ZIP file with .zip.enc extension

---

<div align="center">

### 🌟 Star this repository if you find it helpful!

**Made with ❤️ by [Rajib Kumar Dhar](https://github.com/Rajibkd1)**

*Securing your digital life, one file at a time.*

</div>
