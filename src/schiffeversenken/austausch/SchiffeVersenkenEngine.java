package schiffeversenken.austausch;

public class SchiffeVersenkenEngine implements SchiffeVersenkenEmpfangen, SchiffeVersenkenSenden {

    private SchiffeVersenkenStatus status;

    @Override
    public void reihenfolgeWuerfeln(int i) throws StatusException {
        if (status != SchiffeVersenkenStatus.SPIELSTART) {
            throw new StatusException();
        }
    }

    @Override
    public void wuerfelEmpfangen() throws StatusException {
        if (status != SchiffeVersenkenStatus.SPIELSTART) {
            throw new StatusException();
        }
    }

    @Override
    public void empfangeKoordinate() throws StatusException {
        if (status != SchiffeVersenkenStatus.VERSENKEN) {
            throw new StatusException();
        }
    }

    @Override
    public void empfangeKapitulation() throws StatusException {
        if (status != SchiffeVersenkenStatus.VERSENKEN) {
            throw new StatusException();
        }
    }

    @Override
    public void empfangeBestaetigen() throws StatusException {
        if (status != SchiffeVersenkenStatus.BESTAETIGEN) {
            throw new StatusException();
        }
    }


    @Override
    public void sendeKoordinate(int x, int y) throws StatusException {
        if (status != SchiffeVersenkenStatus.VERSENKEN) {
            throw new StatusException();
        }
    }

    @Override
    public void sendeKapitulation() throws StatusException {
        if (status != SchiffeVersenkenStatus.VERSENKEN) {
            throw new StatusException();
        }
    }

    @Override
    public void sendeBestaetigen(int i) throws StatusException {
        if (status != SchiffeVersenkenStatus.BESTAETIGEN) {
            throw new StatusException();
        }
    }

    public void ende() throws StatusException {
        if (status != SchiffeVersenkenStatus.BEENDEN) {
            throw new StatusException();
        }
    }

}
