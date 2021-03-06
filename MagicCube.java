import java.awt.Color;
import java.awt.Graphics;

public class MagicCube extends Object
{
    public double rotateSpeedY = 0;
    public double rotateSpeedZ = 0;
    public Point3D rotateCenter;

    public static PointDisplayTranslater.RotateMode rotateMode = PointDisplayTranslater.RotateMode.WORLD_CENTER; 

    public MagicCube(int size)
    {
        this(size, new Point3D(0, 0, 0));
    }

    public MagicCube(int size, Point3D spawnPoint)
    {
        this.center = spawnPoint;

        Point3D front_top_left = new Point3D(-size + spawnPoint.x, -size + spawnPoint.y, size + spawnPoint.z);
        Point3D front_top_right = new Point3D(-size + spawnPoint.x, size + spawnPoint.y, size + spawnPoint.z);
        Point3D front_down_left = new Point3D(-size + spawnPoint.x, -size + spawnPoint.y, -size + spawnPoint.z);
        Point3D front_down_right = new Point3D(-size + spawnPoint.x, size + spawnPoint.y, -size + spawnPoint.z);
        Point3D back_top_left = new Point3D(size + spawnPoint.x, -size + spawnPoint.y, size + spawnPoint.z);
        Point3D back_top_right = new Point3D(size + spawnPoint.x, size + spawnPoint.y, size + spawnPoint.z);
        Point3D back_down_left = new Point3D(size + spawnPoint.x, -size + spawnPoint.y, -size + spawnPoint.z);
        Point3D back_down_right = new Point3D(size + spawnPoint.x, size + spawnPoint.y, -size + spawnPoint.z);

        magicPoints.add(front_top_left);
        magicPoints.add(front_top_right);
        magicPoints.add(front_down_left);
        magicPoints.add(front_down_right);
        magicPoints.add(back_top_left);
        magicPoints.add(back_top_right);
        magicPoints.add(back_down_left);
        magicPoints.add(back_down_right);

        Polygon3D front = new Polygon3D(Color.RED, front_top_left, front_top_right, front_down_right, front_down_left);
        Polygon3D back = new Polygon3D(Color.CYAN, back_top_left, back_top_right, back_down_right, back_down_left);
        Polygon3D top = new Polygon3D(Color.ORANGE, front_top_left, front_top_right, back_top_right, back_top_left);
        Polygon3D down = new Polygon3D(Color.BLUE, back_down_left, back_down_right, front_down_right, front_down_left);
        Polygon3D left = new Polygon3D(Color.BLACK, front_top_left, back_top_left, back_down_left, front_down_left);
        Polygon3D right = new Polygon3D(Color.YELLOW, back_top_right, front_top_right, front_down_right, back_down_right);

        magicPoligons.add(front);
        magicPoligons.add(back);
        magicPoligons.add(top);
        magicPoligons.add(down);
        magicPoligons.add(left);
        magicPoligons.add(right);

        sortPolygons();
    }

    public void render(Graphics g)
    {
        sortPolygons();

        for (Polygon3D polygon3d : magicPoligons) 
        {
            polygon3d.render(g);    
        }
    }

    public void update()
    {
        if (Display.mouseLeft) {
            rotateSpeedY = - Display.mouseDeltaY / 2;
            rotateSpeedZ = Display.mouseDeltaX / 2;
        }

        switch(rotateMode) 
        {
            case WORLD_CENTER:
                rotateCenter = PointDisplayTranslater.worldCenter;
                break;

            case SELF_CENTER:
                rotateCenter = center;
                break;
        }

        if (rotateSpeedY > 0.05 || (rotateSpeedY < -0.05)) {
            rotateAxisY(rotateSpeedY, rotateCenter);
            rotateSpeedY *= 0.93;
        }

        if (rotateSpeedZ > 0.05 || (rotateSpeedZ < -0.05)) {
            rotateAxisZ(rotateSpeedZ, rotateCenter);
            rotateSpeedZ *= 0.93;
        }
    }

    public void rotateAxisX(double degrees, Point3D rotateCenter)
    {
        for (Point3D point3d : magicPoints) 
        {
            Point3D locateCoordinat = new Point3D(point3d.x - rotateCenter.x, point3d.y - rotateCenter.y, point3d.z - rotateCenter.z);

            double radius = Math.sqrt(locateCoordinat.y * locateCoordinat.y + locateCoordinat.z * locateCoordinat.z);
            double angle = Math.atan2(locateCoordinat.y, locateCoordinat.z);
            angle += Math.toRadians(degrees);

            point3d.y = radius * Math.sin(angle) + rotateCenter.y;
            point3d.z = radius * Math.cos(angle) + rotateCenter.z;
        }
    }

    public void rotateAxisY(double degrees, Point3D rotateCenter)
    {
        for (Point3D point3d : magicPoints) 
        {
            Point3D locateCoordinat = new Point3D(point3d.x - rotateCenter.x, point3d.y - rotateCenter.y, point3d.z - rotateCenter.z);

            double radius = Math.sqrt(locateCoordinat.x * locateCoordinat.x + locateCoordinat.z * locateCoordinat.z);
            double angle = Math.atan2(locateCoordinat.x, locateCoordinat.z);
            angle += Math.toRadians(degrees);

            point3d.x = radius * Math.sin(angle) + rotateCenter.x;
            point3d.z = radius * Math.cos(angle) + rotateCenter.z;
        }
    }

    public void rotateAxisZ(double degrees, Point3D rotateCenter)
    {
        for (Point3D point3d : magicPoints) 
        {
            Point3D locateCoordinat = new Point3D(point3d.x - rotateCenter.x, point3d.y - rotateCenter.y, point3d.z - rotateCenter.z);

            double radius = Math.sqrt(locateCoordinat.x * locateCoordinat.x + locateCoordinat.y * locateCoordinat.y);
            double angle = Math.atan2(locateCoordinat.x, locateCoordinat.y);
            angle += Math.toRadians(degrees);

            point3d.x = radius * Math.sin(angle) + rotateCenter.x;
            point3d.y = radius * Math.cos(angle) + rotateCenter.y;
        }
    }
} 
