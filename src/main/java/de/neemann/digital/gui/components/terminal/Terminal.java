package de.neemann.digital.gui.components.terminal;

import de.neemann.digital.core.Node;
import de.neemann.digital.core.NodeException;
import de.neemann.digital.core.ObservableValue;
import de.neemann.digital.core.ObservableValues;
import de.neemann.digital.core.element.Element;
import de.neemann.digital.core.element.ElementAttributes;
import de.neemann.digital.core.element.ElementTypeDescription;
import de.neemann.digital.core.element.Keys;
import de.neemann.digital.lang.Lang;

import static de.neemann.digital.core.element.PinInfo.input;

/**
 * @author hneemann
 */
public class Terminal extends Node implements Element {

    /**
     * The terminal description
     */
    public static final ElementTypeDescription DESCRIPTION
            = new ElementTypeDescription(Terminal.class,
            input("D", Lang.get("elem_Terminal_pin_D")),
            input("C", Lang.get("elem_Terminal_pin_C")))
            .addAttribute(Keys.TERM_WIDTH)
            .addAttribute(Keys.TERM_HEIGHT)
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.LABEL);

    private final ElementAttributes attr;
    private ObservableValue data;
    private ObservableValue clock;
    private TerminalDialog terminalDialog;
    private boolean lastClock;

    /**
     * Creates a new terminal instance
     *
     * @param attributes the attributes
     */
    public Terminal(ElementAttributes attributes) {
        attr = attributes;
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        data = inputs.get(0);
        clock = inputs.get(1).addObserverToValue(this).checkBits(1, this);
    }

    @Override
    public ObservableValues getOutputs() {
        return new ObservableValues();
    }

    @Override
    public void readInputs() throws NodeException {
        boolean clockVal = clock.getBool();
        if (!lastClock && clockVal) {
            if (terminalDialog == null)
                terminalDialog = new TerminalDialog(attr);
            terminalDialog.addChar((char) data.getValue());
        }
        lastClock = clockVal;
    }

    @Override
    public void writeOutputs() throws NodeException {
    }

}
