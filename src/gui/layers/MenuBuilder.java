/**
 *
 * @date Jun 11, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package gui.layers;

import domein.enums.GeneratorType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public abstract class MenuBuilder implements Controller {

    private static Menu[] menus;

    private static Menu oscillators, data, filters, tools, output;

    private static MenuItem osc, noise, lfo;
    private static MenuItem env, sequencer, gatesequencer, ramp;
    private static MenuItem filter, delay;
    private static MenuItem clockdivider, multiple, multiply;
    private static MenuItem vca;

    public static Menu[] getMenus() {
        if (menus == null) {
            initMenus();
        }
        return menus;
    }

    private static void initMenus() {
        oscillators = new Menu("Generators");
        data = new Menu("Sequential Data");
        filters = new Menu("Filters");
        tools = new Menu("Tools");
        output = new Menu("Output");

        initOscillators();
        initData();
        initFilters();
        initTools();
        initOutput();

        menus = new Menu[]{oscillators, data, filters, tools, output};
    }

    private static void initOscillators() {
        lfo = new MenuItem("LFO");
        osc = new MenuItem("VCO");
        noise = new MenuItem("Noise");
        initOscillatorAccelerators();
        initOscillatorListeners();
        oscillators.getItems().addAll(lfo, osc, noise);
    }

    private static void initData() {
        env = new MenuItem("ADSR");
        sequencer = new MenuItem("Sequencer");
        gatesequencer = new MenuItem("Gate Sequencer");
        ramp = new MenuItem("Ramp");
        initDataListeners();
        data.getItems().addAll(env, sequencer, gatesequencer, ramp);
    }

    private static void initFilters() {
        filter = new MenuItem("Filter");
        delay = new MenuItem("Delay");
        initFiltersListeners();
        filters.getItems().addAll(filter, delay);
    }

    private static void initTools() {
        clockdivider = new MenuItem("Clock Divider");
        multiple = new MenuItem("Multiple");
        multiply = new MenuItem("Multiply");
        initToolsListeners();
        tools.getItems().addAll(clockdivider, multiple, multiply);
    }

    private static void initOutput() {
        vca = new MenuItem("VCA");
        initOutputListeners();
        output.getItems().addAll(vca);
    }

    private static void initOscillatorListeners() {
        lfo.setOnAction(e -> mainPane.create(GeneratorType.LFO));
        osc.setOnAction(e -> mainPane.create(GeneratorType.OSCILLATOR));
        noise.setOnAction(e -> mainPane.create(GeneratorType.NOISE));
    }

    private static void initDataListeners() {
        env.setOnAction(e -> mainPane.create(GeneratorType.ENVELOPE));
        sequencer.setOnAction(e -> mainPane.create(GeneratorType.SEQUENCER));
        gatesequencer.setOnAction(e -> mainPane.create(GeneratorType.GATESEQUENCER));
        ramp.setOnAction(e -> mainPane.create(GeneratorType.RAMP));
    }

    private static void initFiltersListeners() {
        filter.setOnAction(e -> mainPane.create(GeneratorType.FILTER));
        delay.setOnAction(e -> mainPane.create(GeneratorType.DELAY));
    }

    private static void initToolsListeners() {
        clockdivider.setOnAction(e -> mainPane.create(GeneratorType.CLOCKDIVIDER));
        multiple.setOnAction(e -> mainPane.create(GeneratorType.MULTIPLE));
        multiply.setOnAction(e -> mainPane.create(GeneratorType.MULTIPLY));
    }

    private static void initOutputListeners() {
        vca.setOnAction(e -> mainPane.create(GeneratorType.VCA));
    }
    
    private static void initOscillatorAccelerators() {
        //oscillators.setOnShown(e -> {
            lfo.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
        //});
    }

}
