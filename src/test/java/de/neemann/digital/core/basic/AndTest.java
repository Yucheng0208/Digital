package de.neemann.digital.core.basic;

import de.neemann.digital.TestExecuter;
import de.neemann.digital.core.Model;
import de.neemann.digital.core.ObservableValue;
import de.neemann.digital.core.ObservableValues;
import de.neemann.digital.core.element.ElementAttributes;
import junit.framework.TestCase;

/**
 * @author hneemann
 */
public class AndTest extends TestCase {

    public void testAnd() throws Exception {
        ObservableValue a = new ObservableValue("a", 1);
        ObservableValue b = new ObservableValue("b", 1);

        Model model = new Model();
        FanIn out = model.add(new And(new ElementAttributes().setBits(1)));
        out.setInputs(new ObservableValues(a, b));

        TestExecuter sc = new TestExecuter(model).setInputs(a, b).setOutputs(out.getOutputs());
        sc.check(0, 0, 0);
        sc.check(1, 0, 0);
        sc.check(0, 1, 0);
        sc.check(1, 1, 1);
        sc.check(1, 0, 0);
        sc.check(0, 1, 0);
    }
}