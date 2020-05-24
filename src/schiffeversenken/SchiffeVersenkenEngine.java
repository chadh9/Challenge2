package schiffeversenken;

public class SchiffeVersenkenEngine implements SchiffeVersenkenEmpfangen, SchiffeVersenkenSenden {

    private SchiffeVersenkenStatus status;

    @Override
    public void wuerfelEmpfangen() throws StatusException {
        if(status!=SchiffeVersenkenStatus.SPIELSTART){
            throw new StatusException();
        }


    }

    @Override
    public void empfangeKoordinate() {

    }

    @Override
    public void empfangeKapitulation() {

    }

    @Override
    public void empfangeBestaetigen() {

    }

    @Override
    public void reihenfolgeWuerfeln(int i) {

    }

    @Override
    public void sendeKoordinate(int x, int y) {

    }

    @Override
    public void sendeKapitulation() {

    }

    @Override
    public void sendeBestaetigen(int i) {

    }
}
