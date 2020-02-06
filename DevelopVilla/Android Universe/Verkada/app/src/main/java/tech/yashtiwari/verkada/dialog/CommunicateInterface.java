package tech.yashtiwari.verkada.dialog;

public interface CommunicateInterface {

    /**
     *
     * @param pos - grid position
     * @param add - add = TRUE, remove = FALSE
     */
    public void pushDataToAdapterList(int pos, boolean add);
}
