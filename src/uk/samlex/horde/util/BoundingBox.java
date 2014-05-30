package uk.samlex.horde.util;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class BoundingBox {

    private Vector minimum, maximum;

    public BoundingBox(Vector pointOne, Vector pointTwo) {
        this.minimum = Vector.getMinimum(pointOne, pointTwo);
        this.maximum = Vector.getMaximum(pointOne, pointOne);
    }

    public boolean withinBoundingBox(Vector point) {
        if (point.getBlockX() >= minimum.getBlockX() && point.getBlockX() <= maximum.getBlockX()) {
            if (point.getBlockY() >= minimum.getBlockY() && point.getBlockY() <= maximum.getBlockY()) {
                if (point.getBlockZ() >= minimum.getBlockZ() && point.getBlockZ() <= maximum.getBlockZ()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void previewBoundingBox(BoundingBoxPreviewType type, World world, Material mat, boolean replace) {
        switch (type) {
            case CORNERS:
                previewCorners(world, mat, replace);
                break;
            case FILL:
                previewFill(world, mat, replace);
                break;
            case HOLLOW:
                previewHollow(world, mat, replace);
                break;
            case OUTLINE:
                previewOutline(world, mat, replace);
                break;
        }
    }

    private void previewCorners(World world, Material mat, boolean replace) {
        setBlockAt(world, minimum.getBlockX(), minimum.getBlockY(), minimum.getBlockZ(), mat, replace);
        setBlockAt(world, minimum.getBlockX(), maximum.getBlockY(), minimum.getBlockZ(), mat, replace);
        setBlockAt(world, maximum.getBlockX(), minimum.getBlockY(), minimum.getBlockZ(), mat, replace);
        setBlockAt(world, maximum.getBlockX(), maximum.getBlockY(), minimum.getBlockZ(), mat, replace);

        setBlockAt(world, minimum.getBlockX(), minimum.getBlockY(), maximum.getBlockZ(), mat, replace);
        setBlockAt(world, minimum.getBlockX(), maximum.getBlockY(), maximum.getBlockZ(), mat, replace);
        setBlockAt(world, maximum.getBlockX(), minimum.getBlockY(), maximum.getBlockZ(), mat, replace);
        setBlockAt(world, maximum.getBlockX(), maximum.getBlockY(), maximum.getBlockZ(), mat, replace);

    }

    private void previewFill(World world, Material mat, boolean replace) {
        for (int x = minimum.getBlockX(); x <= maximum.getBlockX(); x++) {
            for (int y = minimum.getBlockY(); y <= maximum.getBlockY(); y++) {
                for (int z = minimum.getBlockZ(); z <= maximum.getBlockZ(); z++) {
                    setBlockAt(world, x, y, z, mat, replace);
                }
            }
        }
    }

    private void previewOutline(World world, Material mat, boolean replace) {
        for (int x = minimum.getBlockX(); x <= maximum.getBlockX(); x++) {
            setBlockAt(world, x, minimum.getBlockY(), minimum.getBlockZ(), mat, replace);
            setBlockAt(world, x, minimum.getBlockY(), maximum.getBlockZ(), mat, replace);
            setBlockAt(world, x, maximum.getBlockY(), minimum.getBlockZ(), mat, replace);
            setBlockAt(world, x, maximum.getBlockY(), maximum.getBlockZ(), mat, replace);
        }
        for (int y = minimum.getBlockY(); y <= maximum.getBlockY(); y++) {
            setBlockAt(world, minimum.getBlockX(), y, minimum.getBlockZ(), mat, replace);
            setBlockAt(world, minimum.getBlockX(), y, maximum.getBlockZ(), mat, replace);
            setBlockAt(world, maximum.getBlockX(), y, minimum.getBlockZ(), mat, replace);
            setBlockAt(world, maximum.getBlockX(), y, maximum.getBlockZ(), mat, replace);
        }
        for (int z = minimum.getBlockZ(); z <= maximum.getBlockZ(); z++) {
            setBlockAt(world, minimum.getBlockX(), minimum.getBlockY(), z, mat, replace);
            setBlockAt(world, minimum.getBlockX(), maximum.getBlockY(), z, mat, replace);
            setBlockAt(world, maximum.getBlockX(), minimum.getBlockY(), z, mat, replace);
            setBlockAt(world, maximum.getBlockX(), maximum.getBlockY(), z, mat, replace);
        }
    }

    private void previewHollow(World world, Material mat, boolean replace) {
        for (int y = minimum.getBlockY(); y <= maximum.getBlockY(); y++) {
            for (int x = minimum.getBlockX(); x <= maximum.getBlockX(); x++) {
                setBlockAt(world, x, y, minimum.getBlockZ(), mat, replace);
                setBlockAt(world, x, y, maximum.getBlockZ(), mat, replace);
            }
        }
        for (int z = minimum.getBlockZ(); z <= maximum.getBlockZ(); z++) {
            for (int y = minimum.getBlockY(); y <= maximum.getBlockY(); y++) {
                setBlockAt(world, minimum.getBlockX(), y, z, mat, replace);
                setBlockAt(world, maximum.getBlockX(), y, z, mat, replace);
            }
        }
        for (int x = minimum.getBlockX(); x <= maximum.getBlockX(); x++) {
            for (int z = minimum.getBlockZ(); z <= maximum.getBlockZ(); z++) {
                setBlockAt(world, x, minimum.getBlockY(), z, mat, replace);
                setBlockAt(world, x, maximum.getBlockY(), z, mat, replace);
            }
        }
    }

    private void setBlockAt(World world, int x, int y, int z, Material mat, boolean replace) {
        Block block = world.getBlockAt(x, y, z);

        if (!replace && block.getType() != Material.AIR) {
            return;
        } else {
            block.setType(mat);
        }
    }
}
