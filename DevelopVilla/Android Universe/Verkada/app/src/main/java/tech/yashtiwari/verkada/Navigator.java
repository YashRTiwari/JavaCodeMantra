package tech.yashtiwari.verkada;

import android.os.Bundle;

public interface Navigator {

    /**
     * Interface that shows specific fragments
     * @param bundle - helps to transfer from one fragment to another.
     */
    void moveToBDSSFragment(Bundle bundle);
    void moveToHomeFragment(Bundle bundle);
}
