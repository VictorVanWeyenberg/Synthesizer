/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import com.jsyn.unitgen.FilterBiquadCommon;
import domein.enums.Filters;

public class Filter extends FilterBiquadCommon {

    private Filters filter;

    public Filter() {
        filter = Filters.LOWPASS;
    }
    
    public void setFilter(Filters filter) {
        this.filter = filter;
    }

    @Override
    public void updateCoefficients() {
        
        double scalar = 1.0 / (1.0 + alpha);
        double cosine;
        double a0_a2_value;
        
        switch (filter) {
            case LOWPASS:
                cosine = 1.0 - cos_omega;
                a0_a2_value = cosine * 0.5 * scalar;

                this.a0 = a0_a2_value;
                this.a1 = cosine * scalar;
                this.a2 = a0_a2_value;
                this.b1 = -2.0 * cos_omega * scalar;
                this.b2 = (1.0 - alpha) * scalar;
                break;
            case HIGHPASS:
                cosine = 1.0 + cos_omega;
                a0_a2_value = cosine * 0.5 * scalar;

                this.a0 = a0_a2_value;
                this.a1 = -cosine * scalar;
                this.a2 = a0_a2_value;
                this.b1 = -2.0 * cos_omega * scalar;
                this.b2 = (1.0 - alpha) * scalar;
                break;
            case BANDPASS:
                a0 = alpha * scalar;
                a1 = 0.0;
                a2 = -a0;
                b1 = -2.0 * cos_omega * scalar;
                b2 = (1.0 - alpha) * scalar;
                break;
        }
    }

}
