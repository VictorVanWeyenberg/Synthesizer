/**
 *
 * @date Jun 13, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package domein;

public class EnumContainingValue<E extends Enum<E>> {

    private E value;
    
    public EnumContainingValue(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
    
}
