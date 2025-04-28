package com.example.thenotelobster;

import com.example.thenotelobster.QuizClasses.QuizResponse;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class NewQuizController extends NavigationUI {

    @FXML Button backButton;

    @FXML Button createQuizButton;

    @FXML private TreeView<String> notesTreeView;
    @FXML private ProgressIndicator LoadingIndicator;

    @FXML
    protected void onBackButtonClick() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("quiz-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);

    }

    @FXML
    public void initialize() {
        TreeItem<String> rootItem = new TreeItem<>("My Notes");
        rootItem.setExpanded(true);

        TreeItem<String> note1 = new TreeItem<>("Dragon Notes");
        TreeItem<String> note2 = new TreeItem<>("History Notes");
        TreeItem<String> note3 = new TreeItem<>("Math Notes");

        rootItem.getChildren().addAll(note1, note2, note3);

        notesTreeView.setRoot(rootItem);
    }


    @FXML
    public void onCreateQuizButtonClick()
    {
        String selectedNote = notesTreeView.getSelectionModel().getSelectedItem().getValue();
        String text = "No note selected";
        if (selectedNote.equals("Dragon Notes"))
        {
            text = "Fire Breathing Dragon Physiology\n" +
                    "To breathe fire, a dragon needs:\n" +
                    "A Fuel Source – What burns?\n" +
                    "An Ignition System – What starts the fire?\n" +
                    "A Delivery System – How does it come out?\n" +
                    "Let’s build a consistent system for all fire-breathing dragons.\n" +
                    "Step 1: Fuel Source (The Fire Gland System)\n" +
                    "Fire-breathing dragons produce a highly flammable liquid stored in specialized fire glands in the chest or throat.\n" +
                    "This liquid could be similar to napalm or petroleum-based compounds, allowing it to burn even on water.\n" +
                    "Possible real-world inspirations:\n" +
                    "Bombardier Beetles (which spray boiling chemicals from their abdomens).\n" +
                    "Deep-sea fish that produce bioluminescent secretions.\n" +
                    "Oil-producing animals, like some birds that coat their feathers in waterproofing oils.\n" +
                    "Composition of Fire Gland Fluid:\n" +
                    "A mix of hydrocarbons (like methane, ethane, or a dragon-specific fuel).\n" +
                    "Stored in a pressurized internal sac near the lungs.\n" +
                    "The dragon releases it in controlled bursts.\n" +
                    "Step 2: Ignition System\n" +
                    "Simply spraying flammable fluid isn’t enough—it needs a spark.\n" +
                    "The dragon’s ignition system could work in a few ways, but we’ll go with the best method for consistency.\n" +
                    "Best Ignition Method: Piezoelectric Sparks\n" +
                    "The dragon has specialized piezoelectric crystals in its mouth (like quartz in its teeth or the back of its throat).\n" +
                    "When the dragon snaps its jaws shut, the pressure creates an electric spark (similar to how a lighter works).\n" +
                    "The spark ignites the flammable fluid as it exits the mouth, creating a controlled, directed burst of fire.\n" +
                    "Why Piezoelectric Sparks?\n" +
                    "No need for external oxygen—works even in enclosed spaces.\n" +
                    " Reliable—consistent ignition every time.\n" +
                    " Doesn’t require complex digestion of volatile materials.\n" +
                    " Scales well for different dragon sizes and fire intensities.\n" +
                    "Step 3: Fire Delivery System\n" +
                    "The dragon expels liquid fuel in a controlled spray.\n" +
                    "The ignition spark turns the liquid into a blazing inferno.\n" +
                    "The fire is released in one of three attack types:\n" +
                    "Cone Attack – A wide spread of fire for burning multiple enemies.\n" +
                    "Focused Stream – A flamethrower-like effect for melting a single target.\n" +
                    "Fireball Burst – The dragon spits a concentrated ball of flaming fuel, which sticks and burns for longer.\n" +
                    "Bonus Detail: Fire breathers may have heat-resistant mouth linings similar to how vultures’ stomachs resist acid.\n" +
                    "Step 4: Biological Adaptations for Fire Breath\n" +
                    "Since dragons breathe fire regularly, their bodies need to handle the heat:\n" +
                    "1. Heat-Resistant Scales & Mucus Lining\n" +
                    "Their mouths, throats, and nasal passages are lined with a special mucus layer that absorbs heat and prevents burns.\n" +
                    "Their scales may contain ceramic-like elements, acting as insulation.\n" +
                    "2. Smoke Venting System\n" +
                    "To prevent smoke inhalation, dragons have secondary airways that vent excess gases.\n" +
                    "Some fire-breathing dragons might even release small puffs of smoke from their nostrils when idle.\n" +
                    "3. Metabolic Considerations\n" +
                    "Producing fuel takes energy, so fire-breathing dragons need a diet rich in fats and hydrocarbons.\n" +
                    "Some may even eat oil-rich plants, tar pits, or coal deposits to replenish their fire glands.\n" +
                    "Final Summary – How Fire Breathing Works\n" +
                    "Fuel Production: Dragon produces a flammable hydrocarbon liquid in its fire glands (near lungs).\n" +
                    "Storage: The fluid is kept pressurized inside the gland.\n" +
                    "Release: When breathing fire, the dragon expels this liquid in a fine mist or stream.\n" +
                    "Ignition: Piezoelectric crystals in the throat create a spark, igniting the spray into flames.\n" +
                    "Controlled Fire Types: The dragon adjusts spray pressure and angles to create either a wide cone, focused beam, or fireball.\n" +
                    "Fire Dragon Weaknesses\n" +
                    "To keep things balanced, what are their limitations?\n" +
                    "Overuse Exhaustion – Fire breath drains energy, requiring rest or high-calorie food.\n" +
                    "Fuel Depletion – If fire gland stores run out, they must consume more fuel-rich materials.\n" +
                    "Cold Vulnerability – Extreme cold may slow down fuel production or clog the fire glands.\n" +
                    " \n" +
                    "Fire Gland & Fuel Storage\n" +
                    "Fire Gland:\n" +
                    " Dragons have a specialized fire gland that produces and stores a highly flammable, hydrocarbon-rich liquid.\n" +
                    "Fuel Source:\n" +
                    " The hydrocarbons could come from their diet. While animals high in fat can supply these compounds, they might also ingest oil-rich plants or even mineral-rich substances that help catalyze or supplement the process.\n" +
                    "Storage Location:\n" +
                    " The fire gland is a distinct organ located near the lungs. It’s a pressurized sac where the liquid fuel is synthesized and stored, separate from the lungs themselves.\n" +
                    "Ignition System\n" +
                    "Piezoelectric Sparks:\n" +
                    " When the dragon snaps its mouth shut, specialized piezoelectric crystals (embedded in the teeth or throat area) generate sparks. This spark ignites the fuel as it’s expelled.\n" +
                    "Mechanism Similar to a Lighter:\n" +
                    " The spark and the increased pressure during the snapping motion cause the fuel to be forcefully expelled and ignited in a controlled manner—without relying on external oxygen.\n" +
                    "Additional Adaptations\n" +
                    "Heat-Resistant Mucus:\n" +
                    " The internal lining (mouth, throat, nasal passages) is coated with a special mucus that can withstand extreme heat.\n" +
                    "Smoke Venting:\n" +
                    " A secondary venting system (likely through the nasal cavities) helps expel smoke and excess gases to prevent self-damage during repeated fire breaths.\n" +
                    "Diet & Its Impact on Teeth\n" +
                    "Based on this system, a fire-breathing dragon’s diet would likely include:\n" +
                    "Oil-rich Plants: To help build up the hydrocarbon reserves.\n" +
                    "Animals High in Fat: Providing the necessary hydrocarbons.\n" +
                    "Minerals or Rocks: Possibly ingested to help form or maintain the piezoelectric crystals in their teeth, or to assist with digestive processing of tough, fibrous materials.\n" +
                    "Dragon Teeth Adaptations\n" +
                    "Given this diet and fire-breathing function, their teeth might feature:\n" +
                    "Robust, Serrated Edges: Ideal for tearing through flesh and processing tough, fibrous plant material. Serrations would allow them to grip and shear meat or plant matter efficiently.\n" +
                    "Specialized Regions:\n" +
                    "In the area responsible for generating piezoelectric sparks, teeth might have a rough or crystalline structure. This could involve enamel with embedded crystalline formations that help generate the necessary friction and electrical charge when the jaws snap shut.\n" +
                    "The front teeth (incisors) might be sharper for initial bites, while the back teeth (molars/premolars) could be flatter and broader for grinding, which might help in breaking down materials that contribute to their fuel synthesis.\n" +
                    "Heat Resistance:\n" +
                    " The enamel and underlying structure of their teeth might be specially adapted to withstand high temperatures—possibly incorporating mineral elements that dissipate heat quickly or resist burning.\n" +
                    " \n";
        }

        AIManager aiManager = AIManager.getInstance();

//       aiManager.fetchAsynchronousChatResponse(summary,length, complexity, new MainResponseListener());

        String finalText = text;
        Task<Void> fetchAsynchronousChatResponse = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                System.out.println("Currently working");
                QuizResponse quizResponse = aiManager.fetchQuizResponse(finalText);
                // save to database here
                System.out.println("Obtained Response");
                return null;
            }
        };

        fetchAsynchronousChatResponse.setOnSucceeded(e -> {
            System.out.println("Refreshing going to quiz");

            createQuizButton.setDisable(false);
            LoadingIndicator.setVisible(false);


            Stage stage = (Stage) createQuizButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("quiz-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            scene.getStylesheets().add(checkCurrentMode());
            stage.setScene(scene);

        });
        LoadingIndicator.setVisible(true);
        createQuizButton.setDisable(true);

        new Thread(fetchAsynchronousChatResponse).start();


    }
}
