package uk.samlex.horde.config;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.EntityType;

import uk.samlex.bukkitcommon.util.EnumSort;
import uk.samlex.horde.Horde;

public class WorldConfig {

    private static final EntityType excludedEntityType = EntityType.PLAYER;

    private String worldName;
    private EntityGroup[] groups;
    private HashMap<EntityType, Boolean> entityMap;
    private WorldMode worldMode = WorldMode.NONE;
    private HordeType hordeType = HordeType.RANDOM;
    private TimeType timeType = TimeType.INGAME;
    private TimerType timerType = TimerType.CONSTANT;
    private int ingameTimeStart = 0;
    private int ingameTimeEnd = 24000;
    private boolean sunBlock = false;
    private int minimunSpawnDistance = 20;
    private int maximumSpawnDistance = 50;
    private String serverMessage = "";
    private String targetMessage = "";
    private int timer = 300;
    private int minimumNumberCreatures = 0;
    private int maximumNumberCreatures = 3;
    private Calendar realtimeStart;
    private Calendar realtimeEnd;

    public WorldConfig(String worldName) {
        this.worldName = worldName;

        ConfigStore config = ConfigStore.instance();

        worldMode = config.getConfigEnum(worldName, "world mode", worldMode, WorldMode.class, Horde.instance());
        hordeType = config.getConfigEnum(worldName, "horde type", hordeType, HordeType.class, Horde.instance());
        timeType = config.getConfigEnum(worldName, "time type", timeType, TimeType.class, Horde.instance());
        sunBlock = config.getConfigBoolean(worldName, "sun block", sunBlock);
        minimunSpawnDistance = config.getConfigInt(worldName, "minimum spawn distance", minimunSpawnDistance);
        maximumSpawnDistance = config.getConfigInt(worldName, "maximum spawn distance", maximumSpawnDistance);
        serverMessage = config.getConfigString(worldName, "server message", serverMessage);
        targetMessage = config.getConfigString(worldName, "target message", targetMessage);

        timerType = config.getConfigEnum(worldName, "time.timer type", timerType, TimerType.class, Horde.instance());
        timer = config.getConfigInt(worldName, "time.timer", timer);
        if (timeType == TimeType.INGAME) {
            ingameTimeStart = config.getConfigInt(worldName, "time.time start", ingameTimeStart);
            ingameTimeEnd = config.getConfigInt(worldName, "time.time end", ingameTimeEnd);
        } else if (timeType == TimeType.REALTIME) {
            DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
            String realtimeStartString = "12:00:00am GMT";
            String realtimeEndString = "12:00:00pm GMT";

            realtimeStartString = config.getConfigString(worldName, "time.time start", realtimeStartString);
            realtimeEndString = config.getConfigString(worldName, "time.time end", realtimeEndString);

            try {
                realtimeStart = Calendar.getInstance();
                realtimeStart.setTime(df.parse(realtimeStartString));

                realtimeEnd = Calendar.getInstance();
                realtimeEnd.setTime(df.parse(realtimeEndString));
            } catch (ParseException pe) {
                Horde.instance().getLogger().severe("Invalid date in config. Please check config file");
                timeType = TimeType.INGAME;
            }
        }

        setupEntityGroups(config);

        minimumNumberCreatures = config.getConfigInt(worldName, "random.minimum creatures", minimumNumberCreatures);
        maximumNumberCreatures = config.getConfigInt(worldName, "random.maximum creatures", maximumNumberCreatures);

        setupEntityMap(config);
    }

    private void setupEntityGroups(ConfigStore config) {
        List<String> groupStringList = config.getConfigStringList(worldName, "groups", Arrays.asList(new String[] { "" }));
        groups = new EntityGroup[groupStringList.size()];

        for (int i = 0; i < groups.length; i++) {
            String groupString = groupStringList.get(i);
            groups[i] = EntityGroup.parseGroupString(groupString);
        }
    }

    private void setupEntityMap(ConfigStore config) {
        entityMap = new HashMap<EntityType, Boolean>();

        for (EntityType type : EnumSort.sortEnumArrayAlphabetically(EntityType.values(), EntityType.class)) {
            if (type.isAlive() && type != excludedEntityType) {
                entityMap.put(type, config.getConfigBoolean(worldName, "random.creature." + type.toString().toLowerCase(), false));
            }
        }
    }

    public String getWorldName() {
        return worldName;
    }

    public EntityGroup[] getGroups() {
        return groups;
    }

    public HashMap<EntityType, Boolean> getEntityMap() {
        return entityMap;
    }

    public WorldMode getWorldMode() {
        return worldMode;
    }

    public HordeType getHordeType() {
        return hordeType;
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public TimerType getTimerType() {
        return timerType;
    }

    public int getIngameTimeStart() {
        return ingameTimeStart;
    }

    public int getIngameTimeEnd() {
        return ingameTimeEnd;
    }

    public boolean isSunBlock() {
        return sunBlock;
    }

    public int getMinimunSpawnDistance() {
        return minimunSpawnDistance;
    }

    public int getMaximumSpawnDistance() {
        return maximumSpawnDistance;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public String getTargetMessage() {
        return targetMessage;
    }

    public int getTimer() {
        return timer;
    }

    public int getMinimumNumberCreatures() {
        return minimumNumberCreatures;
    }

    public int getMaximumNumberCreatures() {
        return maximumNumberCreatures;
    }

    public Calendar getRealtimeStart() {
        return realtimeStart;
    }

    public Calendar getRealtimeEnd() {
        return realtimeEnd;
    }
}
