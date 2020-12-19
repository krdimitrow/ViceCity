package viceCity.models.guns;

public class Pistol extends BaseGun {
    protected int bulletsPerShoot;


    public Pistol(String name) {
        super(name, 10, 100);
        this.bulletsPerShoot = 1;

    }

    @Override
    public void setCanFire() {
        this.canFire = this.getBulletsPerBarrel() >= 1;
    }

    @Override
    public int fire() {
        setBulletsPerBarrel(getBulletsPerBarrel() - bulletsPerShoot);
        if (getBulletsPerBarrel() == 0) {
            this.setTotalBullets(this.getTotalBullets() - 10);
            this.setBulletsPerBarrel(10);
        }

        if (getTotalBullets() == 0) {
            return 0;
        }

        return this.bulletsPerShoot;
    }
}
