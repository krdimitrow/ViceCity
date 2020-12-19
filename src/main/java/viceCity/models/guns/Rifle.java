package viceCity.models.guns;

public class Rifle extends BaseGun {
    protected int bulletsPerShoot;


    public Rifle(String name) {
        super(name, 50, 500);
        this.bulletsPerShoot = 5;

    }

    @Override
    public void setCanFire() {
        if (this.getBulletsPerBarrel() >= 5) {
            this.canFire = true;
        } else {
            this.canFire = false;
        }
    }

    @Override
    public int fire() {
        setBulletsPerBarrel(getBulletsPerBarrel() - bulletsPerShoot);
        if (getBulletsPerBarrel() == 0) {
            this.setTotalBullets(this.getTotalBullets() - 50);
            this.setBulletsPerBarrel(50);
        }

        if (getTotalBullets() == 0) {
            return 0;
        }

        return this.bulletsPerShoot;
    }
}
