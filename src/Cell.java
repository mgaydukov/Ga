public class Cell {
    private boolean status;
    private boolean newStatus;

    public Cell() {
        status = false;
    }

    public Cell(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        newStatus = status;
    }

    public void updateStatus(){
        status = newStatus;
    }

}