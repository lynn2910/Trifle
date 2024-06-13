package trifleGraphic.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import rules.BotStrategy;
import rules.GameMode;
import rules.PlayerMode;
import trifleGraphic.boardifierGraphic.view.RootPane;

public class TrifleRootPane extends RootPane {
    public TrifleRootPane() {
        super();
    }

    @Override
    public void createDefaultGroup(){
        Rectangle frame = new Rectangle(600, 500, Color.TRANSPARENT);
        Text text = new Text("Playing to the Kamisado");
        text.setFont(new Font(15));
        text.setFill(Color.BLACK);
        text.setX(20);
        text.setY(22);
        // put shapes in the group
        group.getChildren().clear();
        group.getChildren().addAll(frame, text);
        group.getChildren().add(createConfigPanel());
    }

    public static String firstPlayerName  = "";
    public static String secondPlayerName = "";

    public static BotStrategy firstPlayerStrategy = BotStrategy.DEFAULT;
    public static BotStrategy secondPlayerStrategy = BotStrategy.DEFAULT;

    public static boolean isFirstPlayerBot = false;
    public static boolean isSecondPlayerBot = false;

    public static GameMode gameMode = GameMode.Fast;
    /**
     * This property is automatically determined by the bot checkbox
     */
    public static PlayerMode playerMode = PlayerMode.HumanVsHuman;


    private final Text playerModeText = new Text(playerMode.toString());

    private void updatePlayerModeText(){
        playerModeText.setText(playerMode.toString());
    }

    private ScrollPane createConfigPanel(){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPadding(new Insets(30, 10, 10, 10));
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        VBox content = new VBox();
        content.getChildren().add(createPlayerNamesPanel());

        Separator separator = new Separator();
        separator.setStyle("-fx-height: 2px; -fx-color: #000; -fx-margin: 10px 0");
        content.getChildren().add(separator);

        scrollPane.setContent(content);
        return scrollPane;
    }

    private VBox createPlayerNamesPanel(){
        VBox playerNames = new VBox();
        playerNames.setSpacing(10);

        Text title = new Text("Players");
        title.setStyle("-fx-font-weight: bold");

        playerNames.getChildren().add(title);

        // create player cards
        HBox playerCards = new HBox();
        playerCards.setSpacing(50);
        playerCards.setPadding(new Insets(0, 25, 0, 25));
        playerCards.setAlignment(Pos.CENTER);
        playerCards.getChildren().addAll(createPlayerConfigCard(0), createPlayerConfigCard(1));

        playerNames.getChildren().add(playerCards);

        return playerNames;
    }

    private VBox createPlayerConfigCard(int playerID){
        VBox playerConfig = new VBox();
        playerConfig.setPrefWidth(255);
        playerConfig.setMaxWidth(255);
        playerConfig.setSpacing(5);

        // Create the body
        HBox body = new HBox();
        body.setAlignment(Pos.CENTER);
        body.setSpacing(10);

        // for a "Human" player
        Label nameLabel = new Label("Name");
        nameLabel.setPrefWidth(150);

        TextField nameField = new TextField();
        nameField.setPrefWidth(210);

        nameField.setPromptText("The name");
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (playerID == 0) firstPlayerName = newValue;
            else secondPlayerName = newValue;
        });

        body.getChildren().addAll(nameLabel, nameField);

        // for a "Bot"
        // TODO cannot see selected item (and some weird width bugs)
        ComboBox<BotStrategy> botStrategy = new ComboBox<>();
        botStrategy.getItems().addAll(BotStrategy.values());
        botStrategy.setVisible(false);

        body.getChildren().addAll(botStrategy);


        // Create player text & checkbox
        HBox topBar = new HBox();
        topBar.setSpacing(30);

        Text text = new Text("Player no." + (playerID + 1));
        text.setWrappingWidth(100);
        topBar.getChildren().add(text);

        CheckBox bot = new CheckBox("Bot");
        bot.setSelected(false);
        bot.setId("bot_config_player");
        bot.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (playerID == 0) isFirstPlayerBot = new_val;
            else isSecondPlayerBot = new_val;

            if (new_val){
                text.setText("Bot no." + (playerID + 1));
                nameField.setVisible(false);
                botStrategy.setVisible(true);

                nameLabel.setText("Strategy");
            } else {
                text.setText("Player no." + (playerID + 1));
                nameField.setVisible(true);
                botStrategy.setVisible(false);

                nameLabel.setText("Name");
            }
        });

        topBar.getChildren().add(bot);

        playerConfig.getChildren().add(topBar);
        playerConfig.getChildren().addAll(body);

        return playerConfig;
    }
}
