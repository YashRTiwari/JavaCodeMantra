package tech.yashtiwari.verkada;

import android.os.Bundle;

public interface Navigator {

    /**
     * Interface that shows specific fragments
     * @param bundle - helps to transfer from one fragment to another.
     */
    public void moveToBDSSFragment(Bundle bundle);
    public void moveToHomeFragment(Bundle bundle);
}
