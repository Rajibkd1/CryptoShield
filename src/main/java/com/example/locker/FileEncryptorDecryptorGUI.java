package com.example.locker;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.application.Platform;
import javafx.util.Duration;
import javafx.animation.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileEncryptorDecryptorGUI extends Application {

    // UI Components
    private TextField filePathField;
    private PasswordField passwordField;
    private RadioButton fileRadioButton;
    private RadioButton folderRadioButton;
    private Stage primaryStage;
    private VBox mainContainer;
    private ProgressIndicator progressIndicator;
    private Label statusLabel;
    private BorderPane mainLayout;

    // Enhanced Style Constants
    private static final String PRIMARY_COLOR = "#667eea";
    private static final String SECONDARY_COLOR = "#764ba2";
    private static final String ACCENT_COLOR = "#f093fb";
    private static final String SUCCESS_COLOR = "#4facfe";
    private static final String ERROR_COLOR = "#f093fb";
    private static final String BACKGROUND_GRADIENT_START = "#667eea";
    private static final String BACKGROUND_GRADIENT_END = "#764ba2";
    private static final String CARD_COLOR = "#ffffff";
    private static final String DARK_TEXT_COLOR = "#2d3748";
    private static final String LIGHT_TEXT_COLOR = "#ffffff";
    private static final String MUTED_TEXT_COLOR = "#718096";
    private static final int BUTTON_WIDTH = 140;
    private static final int BUTTON_HEIGHT = 45;
    private static final int DEFAULT_SPACING = 20;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("CryptoShield - Advanced File Encryption");

        // Add system tray support
        addSystemTraySupport();

        showVerificationScreen();

        // Add particle effect after showing main application
        Platform.runLater(() -> {
            if (primaryStage.getScene() != null) {
                addKeyboardShortcuts(primaryStage.getScene());
                addParticleEffect();
            }
        });
    }

    private void showVerificationScreen() {
        // Create gradient background
        Rectangle background = new Rectangle(600, 500);
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web(BACKGROUND_GRADIENT_START)),
                new Stop(1, Color.web(BACKGROUND_GRADIENT_END)));
        background.setFill(gradient);

        // Animated background circles
        Circle circle1 = new Circle(100, Color.web("#ffffff", 0.1));
        Circle circle2 = new Circle(150, Color.web("#ffffff", 0.05));
        Circle circle3 = new Circle(80, Color.web("#ffffff", 0.08));

        // Position circles
        circle1.setLayoutX(100);
        circle1.setLayoutY(100);
        circle2.setLayoutX(500);
        circle2.setLayoutY(300);
        circle3.setLayoutX(300);
        circle3.setLayoutY(400);

        // Animate circles
        createFloatingAnimation(circle1, 2000);
        createFloatingAnimation(circle2, 3000);
        createFloatingAnimation(circle3, 2500);

        StackPane backgroundPane = new StackPane();
        backgroundPane.getChildren().addAll(background, circle1, circle2, circle3);

        VBox verificationContainer = new VBox(30);
        verificationContainer.setAlignment(Pos.CENTER);
        verificationContainer.setPadding(new Insets(50));

        // App title with enhanced styling
        VBox titleContainer = new VBox(5);
        titleContainer.setAlignment(Pos.CENTER);

        Text titleText = new Text("🔐 CryptoShield");
        titleText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
        titleText.setFill(Color.WHITE);

        Text subtitleText = new Text("Advanced File Protection System");
        subtitleText.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
        subtitleText.setFill(Color.web("#ffffff", 0.8));

        titleContainer.getChildren().addAll(titleText, subtitleText);

        // Enhanced verification card
        VBox verificationCard = createStyledCard();
        verificationCard.setPrefWidth(400);
        verificationCard.setAlignment(Pos.CENTER);

        Label verificationLabel = new Label("🛡️ Security Verification");
        verificationLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        verificationLabel.setTextFill(Color.web(DARK_TEXT_COLOR));

        Label questionLabel = new Label("Solve: 10 + 2 × 3 = ?");
        questionLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
        questionLabel.setTextFill(Color.web(MUTED_TEXT_COLOR));

        TextField verificationAnswerField = new TextField();
        verificationAnswerField.setPromptText("Enter your answer");
        styleEnhancedTextField(verificationAnswerField);
        verificationAnswerField.setPrefWidth(200);

        Button verifyButton = createStyledButton("Verify Access", PRIMARY_COLOR);
        verifyButton.setPrefWidth(200);

        // Add subtle animations to card elements
        FadeTransition cardFade = new FadeTransition(Duration.millis(800), verificationCard);
        cardFade.setFromValue(0);
        cardFade.setToValue(1);

        TranslateTransition cardSlide = new TranslateTransition(Duration.millis(800), verificationCard);
        cardSlide.setFromY(50);
        cardSlide.setToY(0);

        ParallelTransition cardAnimation = new ParallelTransition(cardFade, cardSlide);

        verificationCard.getChildren().addAll(
                verificationLabel,
                new Region() {{ setPrefHeight(10); }},
                questionLabel,
                new Region() {{ setPrefHeight(15); }},
                verificationAnswerField,
                new Region() {{ setPrefHeight(20); }},
                verifyButton
        );

        // Window controls
        HBox windowControls = createWindowControls();

        VBox mainContent = new VBox(40);
        mainContent.setAlignment(Pos.CENTER);
        mainContent.getChildren().addAll(titleContainer, verificationCard);

        BorderPane verificationLayout = new BorderPane();
        verificationLayout.setTop(windowControls);
        verificationLayout.setCenter(mainContent);

        StackPane rootPane = new StackPane();
        rootPane.getChildren().addAll(backgroundPane, verificationLayout);

        // Set verify button action
        verifyButton.setOnAction(e -> {
            String userAnswer = verificationAnswerField.getText().trim();
            if (userAnswer.equals("16")) {
                createSuccessAnimation(verificationCard, () -> showMainApplication());
            } else {
                createErrorAnimation(verificationCard);
                showEnhancedAlert(Alert.AlertType.ERROR, "Access Denied",
                        "Incorrect answer. Please try again.");
            }
        });

        verificationAnswerField.setOnAction(e -> verifyButton.fire());

        Scene verificationScene = new Scene(rootPane, 600, 500);
        verificationScene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(verificationScene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        // Start entrance animation
        cardAnimation.setDelay(Duration.millis(300));
        cardAnimation.play();
    }

    private void showMainApplication() {
        // Create gradient background
        Rectangle background = new Rectangle(800, 700);
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web(BACKGROUND_GRADIENT_START)),
                new Stop(1, Color.web(BACKGROUND_GRADIENT_END)));
        background.setFill(gradient);

        StackPane backgroundPane = new StackPane(background);

        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(0));

        // Create enhanced header
        VBox headerPane = createEnhancedHeader();
        mainLayout.setTop(headerPane);

        // Create main content with better styling
        mainContainer = new VBox(25);
        mainContainer.setPadding(new Insets(40));
        mainContainer.setAlignment(Pos.CENTER);

        // Enhanced toggle section
        VBox toggleSection = createToggleSection();

        // Enhanced status section
        HBox statusContainer = createStatusContainer();

        mainContainer.getChildren().addAll(toggleSection, statusContainer);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        mainLayout.setCenter(scrollPane);

        StackPane rootPane = new StackPane();
        rootPane.getChildren().addAll(backgroundPane, mainLayout);

        Scene mainScene = new Scene(rootPane, 800, 700);
        mainScene.setFill(Color.TRANSPARENT);

        // Enhanced fade-in transition
        FadeTransition fadeIn = new FadeTransition(Duration.millis(600), mainLayout);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(600), mainLayout);
        scaleIn.setFromX(0.9);
        scaleIn.setFromY(0.9);
        scaleIn.setToX(1.0);
        scaleIn.setToY(1.0);

        ParallelTransition entrance = new ParallelTransition(fadeIn, scaleIn);
        entrance.play();

        primaryStage.setScene(mainScene);
        primaryStage.centerOnScreen();

        // Add keyboard shortcuts and particle effects
        addKeyboardShortcuts(mainScene);
        addParticleEffect();
    }

    private VBox createEnhancedHeader() {
        VBox header = new VBox();
        header.setPadding(new Insets(20, 30, 25, 30));

        // Window controls
        HBox windowControls = createWindowControls();

        // Title section
        HBox titleSection = new HBox(15);
        titleSection.setAlignment(Pos.CENTER_LEFT);
        titleSection.setPadding(new Insets(10, 0, 0, 0));

        Text titleText = new Text("🔐 CryptoShield");
        titleText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        titleText.setFill(Color.WHITE);

        VBox titleInfo = new VBox(2);
        Label subtitleLabel = new Label("Advanced File Protection");
        subtitleLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitleLabel.setTextFill(Color.web("#ffffff", 0.8));

        Label versionLabel = new Label("Version 2.0");
        versionLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 12));
        versionLabel.setTextFill(Color.web("#ffffff", 0.6));

        titleInfo.getChildren().addAll(subtitleLabel, versionLabel);
        titleSection.getChildren().addAll(titleText, titleInfo);

        header.getChildren().addAll(windowControls, titleSection);
        return header;
    }

    private HBox createWindowControls() {
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER_RIGHT);
        controls.setPadding(new Insets(10));

        Button minimizeBtn = createWindowButton("−", "#ffc107");
        Button closeBtn = createWindowButton("×", "#dc3545");

        minimizeBtn.setOnAction(e -> primaryStage.setIconified(true));
        closeBtn.setOnAction(e -> {
            createExitAnimation(() -> Platform.exit());
        });

        controls.getChildren().addAll(minimizeBtn, closeBtn);
        return controls;
    }

    private Button createWindowButton(String text, String color) {
        Button button = new Button(text);
        button.setPrefSize(30, 30);
        button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-border-radius: 15px;" +
                        "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(100), button);
            scale.setToX(1.1);
            scale.setToY(1.1);
            scale.play();
        });

        button.setOnMouseExited(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(100), button);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.play();
        });

        return button;
    }

    private VBox createToggleSection() {
        VBox toggleSection = new VBox(20);
        toggleSection.setAlignment(Pos.CENTER);

        Label sectionTitle = new Label("🎯 Choose Operation");
        sectionTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        sectionTitle.setTextFill(Color.WHITE);

        HBox toggleContainer = new HBox(30);
        toggleContainer.setAlignment(Pos.CENTER);

        ToggleGroup toggleGroup = new ToggleGroup();
        fileRadioButton = createEnhancedRadioButton("📄 File Encryption", "Encrypt or decrypt individual files", toggleGroup);
        folderRadioButton = createEnhancedRadioButton("📁 Folder Protection", "Lock or unlock entire folders", toggleGroup);

        toggleContainer.getChildren().addAll(fileRadioButton, folderRadioButton);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == fileRadioButton) {
                showFileOptions();
            } else if (newValue == folderRadioButton) {
                showFolderOptions();
            }
        });

        toggleSection.getChildren().addAll(sectionTitle, toggleContainer);
        return toggleSection;
    }

    private RadioButton createEnhancedRadioButton(String title, String description, ToggleGroup group) {
        RadioButton radioButton = new RadioButton();
        radioButton.setToggleGroup(group);

        VBox content = new VBox(8);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setPrefWidth(250);
        content.setPrefHeight(120);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.web(DARK_TEXT_COLOR));

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        descLabel.setTextFill(Color.web(MUTED_TEXT_COLOR));
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        content.getChildren().addAll(titleLabel, descLabel);

        // Style the radio button container
        content.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-border-radius: 15px;" +
                        "-fx-border-color: #e2e8f0;" +
                        "-fx-border-width: 2px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);" +
                        "-fx-cursor: hand;"
        );

        // Add hover and selection effects
        content.setOnMouseEntered(e -> {
            if (!radioButton.isSelected()) {
                content.setStyle(
                        "-fx-background-color: #f7fafc;" +
                                "-fx-background-radius: 15px;" +
                                "-fx-border-radius: 15px;" +
                                "-fx-border-color: " + PRIMARY_COLOR + ";" +
                                "-fx-border-width: 2px;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15, 0, 0, 8);" +
                                "-fx-cursor: hand;"
                );
            }
            ScaleTransition scale = new ScaleTransition(Duration.millis(150), content);
            scale.setToX(1.05);
            scale.setToY(1.05);
            scale.play();
        });

        content.setOnMouseExited(e -> {
            if (!radioButton.isSelected()) {
                content.setStyle(
                        "-fx-background-color: white;" +
                                "-fx-background-radius: 15px;" +
                                "-fx-border-radius: 15px;" +
                                "-fx-border-color: #e2e8f0;" +
                                "-fx-border-width: 2px;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);" +
                                "-fx-cursor: hand;"
                );
            }
            ScaleTransition scale = new ScaleTransition(Duration.millis(150), content);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.play();
        });

        content.setOnMouseClicked(e -> radioButton.setSelected(true));

        radioButton.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                content.setStyle(
                        "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY_COLOR + ", " + SECONDARY_COLOR + ");" +
                                "-fx-background-radius: 15px;" +
                                "-fx-border-radius: 15px;" +
                                "-fx-border-color: " + PRIMARY_COLOR + ";" +
                                "-fx-border-width: 3px;" +
                                "-fx-effect: dropshadow(gaussian, rgba(102,126,234,0.4), 20, 0, 0, 10);" +
                                "-fx-cursor: hand;"
                );
                titleLabel.setTextFill(Color.WHITE);
                descLabel.setTextFill(Color.web("#ffffff", 0.9));
            } else {
                content.setStyle(
                        "-fx-background-color: white;" +
                                "-fx-background-radius: 15px;" +
                                "-fx-border-radius: 15px;" +
                                "-fx-border-color: #e2e8f0;" +
                                "-fx-border-width: 2px;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);" +
                                "-fx-cursor: hand;"
                );
                titleLabel.setTextFill(Color.web(DARK_TEXT_COLOR));
                descLabel.setTextFill(Color.web(MUTED_TEXT_COLOR));
            }
        });

        StackPane container = new StackPane(content);
        radioButton.setGraphic(container);
        radioButton.setText("");

        return radioButton;
    }

    private HBox createStatusContainer() {
        HBox statusContainer = new HBox(15);
        statusContainer.setAlignment(Pos.CENTER);
        statusContainer.setPadding(new Insets(20));

        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        progressIndicator.setPrefSize(30, 30);
        progressIndicator.setStyle("-fx-accent: " + PRIMARY_COLOR + ";");

        statusLabel = new Label();
        statusLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        statusLabel.setTextFill(Color.WHITE);

        statusContainer.getChildren().addAll(progressIndicator, statusLabel);
        return statusContainer;
    }

    private void showFileOptions() {
        // Remove previous options
        while (mainContainer.getChildren().size() > 2) {
            mainContainer.getChildren().remove(2);
        }

        VBox fileOptionsCard = createStyledCard();
        fileOptionsCard.setPrefWidth(600);
        fileOptionsCard.setAlignment(Pos.CENTER);

        Label optionsTitleLabel = new Label("📄 File Encryption Options");
        optionsTitleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        optionsTitleLabel.setTextFill(Color.web(PRIMARY_COLOR));

        // File selection section
        VBox fileSection = createInputSection("📁 Select File", "Choose a file to encrypt or decrypt");
        filePathField = new TextField();
        filePathField.setPromptText("No file selected...");
        styleEnhancedTextField(filePathField);

        Button browseButton = createStyledButton("Browse Files", PRIMARY_COLOR);
        browseButton.setOnAction(e -> browseFile());

        HBox filePathBox = new HBox(15);
        filePathBox.setAlignment(Pos.CENTER);
        filePathBox.getChildren().addAll(filePathField, browseButton);
        HBox.setHgrow(filePathField, Priority.ALWAYS);

        fileSection.getChildren().add(filePathBox);

        // Password section
        VBox passwordSection = createInputSection("🔐 Security Password", "Enter a strong password for encryption");
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your secure password...");
        styleEnhancedTextField(passwordField);
        passwordSection.getChildren().add(passwordField);

        // Action buttons
        HBox actionButtonsBox = new HBox(20);
        actionButtonsBox.setAlignment(Pos.CENTER);
        actionButtonsBox.setPadding(new Insets(20, 0, 0, 0));

        Button encryptButton = createStyledButton("🔒 Encrypt File", PRIMARY_COLOR);
        Button decryptButton = createStyledButton("🔓 Decrypt File", SECONDARY_COLOR);

        encryptButton.setOnAction(e -> processFileAction(true));
        decryptButton.setOnAction(e -> processFileAction(false));

        actionButtonsBox.getChildren().addAll(encryptButton, decryptButton);

        fileOptionsCard.getChildren().addAll(
                optionsTitleLabel,
                createSpacer(20),
                fileSection,
                createSpacer(15),
                passwordSection,
                actionButtonsBox
        );

        // Add entrance animation
        createCardEntranceAnimation(fileOptionsCard);
        mainContainer.getChildren().add(fileOptionsCard);

        // Setup drag and drop and tooltips
        setupDragAndDrop();
        addTooltips();
    }

    private void showFolderOptions() {
        // Remove previous options
        while (mainContainer.getChildren().size() > 2) {
            mainContainer.getChildren().remove(2);
        }

        VBox folderOptionsCard = createStyledCard();
        folderOptionsCard.setPrefWidth(600);
        folderOptionsCard.setAlignment(Pos.CENTER);

        Label optionsTitleLabel = new Label("📁 Folder Protection Options");
        optionsTitleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        optionsTitleLabel.setTextFill(Color.web(PRIMARY_COLOR));

        // Path selection section
        VBox pathSection = createInputSection("📂 Select Target", "Choose a folder to lock or encrypted file to unlock");
        filePathField = new TextField();
        filePathField.setPromptText("No folder/file selected...");
        styleEnhancedTextField(filePathField);

        HBox browseButtonsBox = new HBox(10);
        Button browseFolderButton = createStyledButton("📁 Folder", PRIMARY_COLOR);
        Button browseFileButton = createStyledButton("📄 File", SECONDARY_COLOR);

        browseFolderButton.setPrefWidth(100);
        browseFileButton.setPrefWidth(100);

        browseFolderButton.setOnAction(e -> browseFolder());
        browseFileButton.setOnAction(e -> browseFile());

        browseButtonsBox.getChildren().addAll(browseFolderButton, browseFileButton);

        HBox pathBox = new HBox(15);
        pathBox.setAlignment(Pos.CENTER);
        pathBox.getChildren().addAll(filePathField, browseButtonsBox);
        HBox.setHgrow(filePathField, Priority.ALWAYS);

        pathSection.getChildren().add(pathBox);

        // Password section
        VBox passwordSection = createInputSection("🔐 Security Password", "Enter a strong password for protection");
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your secure password...");
        styleEnhancedTextField(passwordField);
        passwordSection.getChildren().add(passwordField);

        // Action buttons
        HBox actionButtonsBox = new HBox(20);
        actionButtonsBox.setAlignment(Pos.CENTER);
        actionButtonsBox.setPadding(new Insets(20, 0, 0, 0));

        Button lockButton = createStyledButton("🔒 Lock Folder", PRIMARY_COLOR);
        Button unlockButton = createStyledButton("🔓 Unlock Folder", SECONDARY_COLOR);

        lockButton.setOnAction(e -> processFolderAction(true));
        unlockButton.setOnAction(e -> processFolderAction(false));

        actionButtonsBox.getChildren().addAll(lockButton, unlockButton);

        folderOptionsCard.getChildren().addAll(
                optionsTitleLabel,
                createSpacer(20),
                pathSection,
                createSpacer(15),
                passwordSection,
                actionButtonsBox
        );

        // Add entrance animation
        createCardEntranceAnimation(folderOptionsCard);
        mainContainer.getChildren().add(folderOptionsCard);

        // Setup drag and drop and tooltips
        setupDragAndDrop();
        addTooltips();
    }

    private VBox createInputSection(String title, String description) {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.web(DARK_TEXT_COLOR));

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        descLabel.setTextFill(Color.web(MUTED_TEXT_COLOR));

        section.getChildren().addAll(titleLabel, descLabel);
        return section;
    }

    private Region createSpacer(double height) {
        Region spacer = new Region();
        spacer.setPrefHeight(height);
        return spacer;
    }

    private VBox createStyledCard() {
        VBox card = new VBox(20);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.CENTER);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 20px;" +
                        "-fx-border-radius: 20px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 25, 0, 0, 10);"
        );
        return card;
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));

        button.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, " + color + ", derive(" + color + ", -20%));" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 25px;" +
                        "-fx-border-radius: 25px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);"
        );

        // Enhanced hover effects
        button.setOnMouseEntered(e -> {
            button.setStyle(
                    "-fx-background-color: linear-gradient(to bottom right, derive(" + color + ", 20%), " + color + ");" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 25px;" +
                            "-fx-border-radius: 25px;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 8);"
            );

            ScaleTransition scale = new ScaleTransition(Duration.millis(150), button);
            scale.setToX(1.05);
            scale.setToY(1.05);
            scale.play();
        });

        button.setOnMouseExited(e -> {
            button.setStyle(
                    "-fx-background-color: linear-gradient(to bottom right, " + color + ", derive(" + color + ", -20%));" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 25px;" +
                            "-fx-border-radius: 25px;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);"
            );

            ScaleTransition scale = new ScaleTransition(Duration.millis(150), button);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.play();
        });

        return button;
    }

    private void styleEnhancedTextField(TextField textField) {
        textField.setPrefHeight(45);
        textField.setFont(Font.font("Segoe UI", 14));

        textField.setStyle(
                "-fx-background-color: #f8f9fa;" +
                        "-fx-border-color: #e2e8f0;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-padding: 12px 16px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);"
        );

        // Enhanced focus effects
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                textField.setStyle(
                        "-fx-background-color: white;" +
                                "-fx-border-color: " + PRIMARY_COLOR + ";" +
                                "-fx-border-width: 2px;" +
                                "-fx-border-radius: 12px;" +
                                "-fx-background-radius: 12px;" +
                                "-fx-padding: 12px 16px;" +
                                "-fx-effect: dropshadow(gaussian, rgba(102,126,234,0.3), 10, 0, 0, 5);"
                );
            } else {
                textField.setStyle(
                        "-fx-background-color: #f8f9fa;" +
                                "-fx-border-color: #e2e8f0;" +
                                "-fx-border-width: 2px;" +
                                "-fx-border-radius: 12px;" +
                                "-fx-background-radius: 12px;" +
                                "-fx-padding: 12px 16px;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);"
                );
            }
        });
    }

    // Animation Methods
    private void createFloatingAnimation(Circle circle, int duration) {
        TranslateTransition translate = new TranslateTransition(Duration.millis(duration), circle);
        translate.setByY(-20);
        translate.setAutoReverse(true);
        translate.setCycleCount(Timeline.INDEFINITE);
        translate.play();

        RotateTransition rotate = new RotateTransition(Duration.millis(duration * 2), circle);
        rotate.setByAngle(360);
        rotate.setCycleCount(Timeline.INDEFINITE);
        rotate.play();
    }

    private void createCardEntranceAnimation(VBox card) {
        card.setOpacity(0);
        card.setTranslateY(30);

        FadeTransition fade = new FadeTransition(Duration.millis(500), card);
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition slide = new TranslateTransition(Duration.millis(500), card);
        slide.setFromY(30);
        slide.setToY(0);

        ParallelTransition entrance = new ParallelTransition(fade, slide);
        entrance.play();
    }

    private void createSuccessAnimation(VBox target, Runnable onComplete) {
        // Success pulse effect
        ScaleTransition pulse1 = new ScaleTransition(Duration.millis(150), target);
        pulse1.setToX(1.05);
        pulse1.setToY(1.05);

        ScaleTransition pulse2 = new ScaleTransition(Duration.millis(150), target);
        pulse2.setToX(1.0);
        pulse2.setToY(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), target);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        SequentialTransition success = new SequentialTransition(pulse1, pulse2, fadeOut);
        success.setOnFinished(e -> onComplete.run());
        success.play();
    }

    private void createErrorAnimation(VBox target) {
        // Error shake effect
        TranslateTransition shake1 = new TranslateTransition(Duration.millis(50), target);
        shake1.setToX(10);

        TranslateTransition shake2 = new TranslateTransition(Duration.millis(50), target);
        shake2.setToX(-10);

        TranslateTransition shake3 = new TranslateTransition(Duration.millis(50), target);
        shake3.setToX(5);

        TranslateTransition shake4 = new TranslateTransition(Duration.millis(50), target);
        shake4.setToX(0);

        SequentialTransition shake = new SequentialTransition(shake1, shake2, shake3, shake4);
        shake.play();
    }

    private void createExitAnimation(Runnable onComplete) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), primaryStage.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(300), primaryStage.getScene().getRoot());
        scaleOut.setToX(0.8);
        scaleOut.setToY(0.8);

        ParallelTransition exit = new ParallelTransition(fadeOut, scaleOut);
        exit.setOnFinished(e -> onComplete.run());
        exit.play();
    }

    // File/Folder Operations
    private void browseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            filePathField.setText(selectedFile.getAbsolutePath());
            createFieldUpdateAnimation(filePathField);
        }
    }

    private void browseFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        File selectedFolder = directoryChooser.showDialog(primaryStage);
        if (selectedFolder != null) {
            filePathField.setText(selectedFolder.getAbsolutePath());
            createFieldUpdateAnimation(filePathField);
        }
    }

    private void createFieldUpdateAnimation(TextField field) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), field);
        scale.setToX(1.02);
        scale.setToY(1.02);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.play();
    }

    private void processFileAction(boolean encrypt) {
        String filePath = filePathField.getText();
        String password = passwordField.getText();

        if (filePath.isEmpty() || password.isEmpty()) {
            showEnhancedAlert(Alert.AlertType.WARNING, "Missing Information",
                    "Please provide both file path and password.");
            return;
        }

        // Enhanced progress indication
        progressIndicator.setVisible(true);
        statusLabel.setText(encrypt ? "🔒 Encrypting file..." : "🔓 Decrypting file...");
        statusLabel.setTextFill(Color.WHITE);

        // Disable UI during operation
        setUIEnabled(false);

        new Thread(() -> {
            try {
                if (encrypt) {
                    encryptor(password, filePath);
                    updateStatus("✅ File encrypted successfully!", true);
                } else {
                    decryptor(password, filePath);
                    updateStatus("✅ File decrypted successfully!", true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                updateStatus("❌ Error: " + e.getMessage(), false);
            } finally {
                Platform.runLater(() -> setUIEnabled(true));
            }
        }).start();
    }

    private void processFolderAction(boolean lock) {
        String path = filePathField.getText();
        String password = passwordField.getText();

        if (path.isEmpty() || password.isEmpty()) {
            showEnhancedAlert(Alert.AlertType.WARNING, "Missing Information",
                    "Please provide both path and password.");
            return;
        }

        progressIndicator.setVisible(true);
        statusLabel.setText(lock ? "🔒 Locking folder..." : "🔓 Unlocking folder...");
        statusLabel.setTextFill(Color.WHITE);

        setUIEnabled(false);

        new Thread(() -> {
            try {
                if (lock) {
                    lockFolder(path, password);
                    updateStatus("✅ Folder locked successfully!", true);
                } else {
                    unlockFolder(path, password);
                    updateStatus("✅ Folder unlocked! Please extract manually.", true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                updateStatus("❌ Error: " + e.getMessage(), false);
            } finally {
                Platform.runLater(() -> setUIEnabled(true));
            }
        }).start();
    }

    private void setUIEnabled(boolean enabled) {
        Platform.runLater(() -> {
            filePathField.setDisable(!enabled);
            passwordField.setDisable(!enabled);
            if (fileRadioButton != null) fileRadioButton.setDisable(!enabled);
            if (folderRadioButton != null) folderRadioButton.setDisable(!enabled);
        });
    }

    private void updateStatus(String message, boolean success) {
        Platform.runLater(() -> {
            statusLabel.setText(message);
            statusLabel.setTextFill(success ? Color.web(SUCCESS_COLOR) : Color.web(ERROR_COLOR));
            progressIndicator.setVisible(false);

            // Create status animation
            ScaleTransition statusScale = new ScaleTransition(Duration.millis(300), statusLabel);
            statusScale.setFromX(0.8);
            statusScale.setFromY(0.8);
            statusScale.setToX(1.0);
            statusScale.setToY(1.0);
            statusScale.play();

            // Clear password field for security
            passwordField.clear();

            // Auto-hide status after 5 seconds
            Timeline hideStatus = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
                FadeTransition fade = new FadeTransition(Duration.millis(500), statusLabel);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.setOnFinished(event -> statusLabel.setText(""));
                fade.play();
            }));
            hideStatus.play();
        });
    }

    private void showEnhancedAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Enhanced alert styling
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-border-radius: 15px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 10);"
        );

        // Style alert buttons
        for (ButtonType buttonType : alert.getButtonTypes()) {
            Button button = (Button) dialogPane.lookupButton(buttonType);
            if (button != null) {
                button.setStyle(
                        "-fx-background-color: " + PRIMARY_COLOR + ";" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 8px;" +
                                "-fx-cursor: hand;"
                );
            }
        }

        alert.showAndWait();
    }

    // Additional UI Enhancement Methods
    private void addParticleEffect() {
        // Create subtle particle animation in background
        Timeline particleTimeline = new Timeline();
        particleTimeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), e -> {
            if (Math.random() < 0.1) { // 10% chance each frame
                createParticle();
            }
        });

        particleTimeline.getKeyFrames().add(keyFrame);
        particleTimeline.play();
    }

    private void createParticle() {
        Circle particle = new Circle(2, Color.web("#ffffff", 0.3));
        particle.setLayoutX(Math.random() * 800);
        particle.setLayoutY(700);

        if (mainLayout != null && mainLayout.getParent() instanceof StackPane) {
            StackPane root = (StackPane) mainLayout.getParent();
            root.getChildren().add(0, particle);

            // Animate particle upward
            TranslateTransition rise = new TranslateTransition(Duration.millis(3000), particle);
            rise.setToY(-700);

            FadeTransition fade = new FadeTransition(Duration.millis(3000), particle);
            fade.setFromValue(0.3);
            fade.setToValue(0.0);

            ParallelTransition animation = new ParallelTransition(rise, fade);
            animation.setOnFinished(e -> root.getChildren().remove(particle));
            animation.play();
        }
    }

    private void addKeyboardShortcuts(Scene scene) {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ESCAPE:
                    createExitAnimation(() -> Platform.exit());
                    break;
                case F1:
                    showHelpDialog();
                    break;
                case ENTER:
                    // Trigger action based on current selection
                    if (fileRadioButton != null && fileRadioButton.isSelected()) {
                        if (!filePathField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                            processFileAction(true); // Default to encrypt
                        }
                    } else if (folderRadioButton != null && folderRadioButton.isSelected()) {
                        if (!filePathField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                            processFolderAction(true); // Default to lock
                        }
                    }
                    break;
            }
        });
    }

    private void showHelpDialog() {
        Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
        helpAlert.setTitle("CryptoShield Help");
        helpAlert.setHeaderText("How to use CryptoShield");

        String helpText = """
            🔐 CryptoShield - Advanced File Protection
            📄 File Encryption:
            • Select a file using the Browse button
            • Enter a strong password
            • Click Encrypt to secure your file
            • Click Decrypt to restore your file
            📁 Folder Protection:
            • Select a folder to lock or encrypted file to unlock
            • Enter a strong password
            • Lock compresses and encrypts the entire folder
            • Unlock restores the folder as a ZIP file
            🔑 Security Tips:
            • Use strong, unique passwords
            • Remember your passwords - they cannot be recovered
            • Keep backups of important files
            • Encrypted files have .enc extension
            ⌨️ Keyboard Shortcuts:
            • ESC - Exit application
            • F1 - Show this help
            • ENTER - Execute default action
            Version 2.0 - Enhanced Security & Design
            """;

        helpAlert.setContentText(helpText);

        // Style the help dialog
        DialogPane dialogPane = helpAlert.getDialogPane();
        dialogPane.setPrefWidth(500);
        dialogPane.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-border-radius: 15px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 10);"
        );

        // Style the help button
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        if (okButton != null) {
            okButton.setStyle(
                    "-fx-background-color: " + PRIMARY_COLOR + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 8px;" +
                            "-fx-cursor: hand;"
            );
        }

        helpAlert.showAndWait();
    }

    private void addTooltips() {
        if (filePathField != null) {
            Tooltip pathTooltip = new Tooltip("Select the file or folder you want to encrypt/decrypt");
            styleTooltip(pathTooltip);
            Tooltip.install(filePathField, pathTooltip);
        }

        if (passwordField != null) {
            Tooltip passwordTooltip = new Tooltip("Enter a strong password. Remember it - it cannot be recovered!");
            styleTooltip(passwordTooltip);
            Tooltip.install(passwordField, passwordTooltip);
        }
    }

    private void styleTooltip(Tooltip tooltip) {
        tooltip.setStyle(
                "-fx-background-color: " + DARK_TEXT_COLOR + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-font-size: 12px;" +
                        "-fx-padding: 8px;"
        );
        tooltip.setShowDelay(Duration.millis(500));
        tooltip.setHideDelay(Duration.millis(100));
    }

    private void addProgressAnimation() {
        if (progressIndicator != null) {
            // Custom progress indicator styling
            progressIndicator.setStyle(
                    "-fx-accent: " + PRIMARY_COLOR + ";" +
                            "-fx-control-inner-background: white;"
            );

            // Add rotation animation when visible
            RotateTransition rotation = new RotateTransition(Duration.millis(1000), progressIndicator);
            rotation.setByAngle(360);
            rotation.setCycleCount(Timeline.INDEFINITE);

            progressIndicator.visibleProperty().addListener((obs, wasVisible, isVisible) -> {
                if (isVisible) {
                    rotation.play();
                } else {
                    rotation.stop();
                }
            });
        }
    }

    private void setupDragAndDrop() {
        if (filePathField != null) {
            filePathField.setOnDragOver(event -> {
                if (event.getGestureSource() != filePathField && event.getDragboard().hasFiles()) {
                    event.acceptTransferModes(javafx.scene.input.TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            });

            filePathField.setOnDragDropped(event -> {
                var dragboard = event.getDragboard();
                boolean success = false;

                if (dragboard.hasFiles()) {
                    var files = dragboard.getFiles();
                    if (!files.isEmpty()) {
                        File file = files.get(0);
                        filePathField.setText(file.getAbsolutePath());
                        createFieldUpdateAnimation(filePathField);
                        success = true;
                    }
                }

                event.setDropCompleted(success);
                event.consume();
            });

            // Visual feedback for drag over
            filePathField.setOnDragEntered(event -> {
                if (event.getGestureSource() != filePathField && event.getDragboard().hasFiles()) {
                    filePathField.setStyle(filePathField.getStyle() +
                            "-fx-border-color: " + ACCENT_COLOR + ";" +
                            "-fx-border-width: 3px;"
                    );
                }
                event.consume();
            });

            filePathField.setOnDragExited(event -> {
                styleEnhancedTextField(filePathField);
                event.consume();
            });
        }
    }

    private void addSystemTraySupport() {
        if (java.awt.SystemTray.isSupported()) {
            try {
                java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();

                // Create a simple tray icon (using a basic image)
                java.awt.Image image = new java.awt.image.BufferedImage(16, 16, java.awt.image.BufferedImage.TYPE_INT_RGB);
                java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image, "CryptoShield");

                trayIcon.setImageAutoSize(true);
                trayIcon.addActionListener(e -> Platform.runLater(() -> {
                    primaryStage.show();
                    primaryStage.toFront();
                }));

                tray.add(trayIcon);

                // Hide to tray instead of closing
                primaryStage.setOnCloseRequest(e -> {
                    e.consume();
                    primaryStage.hide();
                });

            } catch (Exception e) {
                System.err.println("Could not add system tray support: " + e.getMessage());
            }
        }
    }

    // Encryption/Decryption Methods (keeping original functionality)
    private static byte[] padFile(byte[] fileData) {
        int blockSize = 16;
        int padding = blockSize - (fileData.length % blockSize);
        byte[] paddedData = new byte[fileData.length + padding];
        System.arraycopy(fileData, 0, paddedData, 0, fileData.length);
        return paddedData;
    }

    private static void encrypt(byte[] keyBytes, byte[] ivBytes, String fileName) throws Exception {
        byte[] fileData = Files.readAllBytes(Paths.get(fileName));
        byte[] paddedFile = padFile(fileData);

        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] encryptedData = encryptCipher.doFinal(paddedFile);

        try (FileOutputStream fos = new FileOutputStream(fileName + ".enc")) {
            fos.write(encryptedData);
        }

        Files.deleteIfExists(Paths.get(fileName));
    }

    private static void decrypt(byte[] keyBytes, byte[] ivBytes, String fileName) throws Exception {
        byte[] encryptedData = Files.readAllBytes(Paths.get(fileName));

        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedData = decryptCipher.doFinal(encryptedData);

        try (FileOutputStream fos = new FileOutputStream(fileName.substring(0, fileName.length() - 4))) {
            fos.write(decryptedData);
        }

        Files.deleteIfExists(Paths.get(fileName));
    }

    private static void encryptor(String password, String fileName) throws Exception {
        System.out.println("Encrypting: " + fileName + " to " + fileName + ".enc");
        byte[] key = MessageDigest.getInstance("SHA-256").digest(password.getBytes());
        byte[] iv = new byte[16];
        System.arraycopy(key, 0, iv, 0, 16);

        encrypt(key, iv, fileName);
        System.out.println(fileName + " has been encrypted.");
    }

    private static void decryptor(String password, String fileName) throws Exception {
        if (!fileName.endsWith(".enc")) {
            fileName = fileName + ".enc";
        }
        System.out.println("Decrypting: " + fileName + " to " + fileName.substring(0, fileName.length() - 4));

        byte[] key = MessageDigest.getInstance("SHA-256").digest(password.getBytes());
        byte[] iv = new byte[16];
        System.arraycopy(key, 0, iv, 0, 16);

        decrypt(key, iv, fileName);
        System.out.println(fileName + " has been decrypted.");
    }

    private static void lockFolder(String folderPath, String password) throws Exception {
        System.out.println("Locking folder: " + folderPath);

        File folder = new File(folderPath);
        String zipFileName = folderPath + ".zip";

        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            zipFile(folder, folder.getName(), zipOut);
        }

        encryptor(password, zipFileName);
        deleteFolder(folder);

        System.out.println("Folder has been locked and compressed as: " + zipFileName + ".enc");
    }

    private static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }

    private static void unlockFolder(String encryptedZipFileName, String password) throws Exception {
        System.out.println("Unlocking folder from: " + encryptedZipFileName);

        if (!encryptedZipFileName.endsWith(".enc")) {
            encryptedZipFileName = encryptedZipFileName + ".enc";
        }

        String decryptedZipFileName = encryptedZipFileName.substring(0, encryptedZipFileName.length() - 4);
        decryptor(password, encryptedZipFileName);

        System.out.println("Folder has been unlocked successfully! Please extract the ZIP file manually.");
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            File[] children = fileToZip.listFiles();
            if (children != null) {
                for (File childFile : children) {
                    zipFile(childFile, fileName + File.separator + childFile.getName(), zipOut);
                }
            }
        } else {
            try (FileInputStream fis = new FileInputStream(fileToZip)) {
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOut.putNextEntry(zipEntry);
                byte[] bytes = new byte[4096];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
            }
        }
    }

    public static void main(String[] args) {
        // Set system properties for better rendering
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");

        launch(args);
    }
}
