package me.accedepro.xpdeathpenalty;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.CauseStackManager;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.ScoreText;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;

@Plugin(id = "xpdeathpenalty", name = "Xpdeathpenalty", version = "1.0")

public class Xpdeathpenalty {

    public Location getloc;
    public int score;
    public int exp;

    public static void main(String args[]) {

    }

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        System.out.println("Starting XPDrop Plugin...");
    }

    @Listener
    public void onServerStop(GameStoppedServerEvent event) {
        System.out.println("Stopping XPDrop Plugin...");
    }

    @Listener
    public void onPlayerDeath(DestructEntityEvent.Death event) {

        if (event.getTargetEntity().getType().equals(EntityTypes.PLAYER)) {
            Entity player = event.getTargetEntity();

            //ScoreText.builder().equals(score);
            //score = (event.getTargetEntity().get(Keys.TOTAL_EXPERIENCE).orElseGet(() -> 0));

            if (player.get(Keys.EXPERIENCE_LEVEL) == null) {
                player.offer(Keys.EXPERIENCE_LEVEL, 0);
            } else if (player.get(Keys.EXPERIENCE_LEVEL) != null) {
                getloc = event.getTargetEntity().getLocation();
                exp = (event.getTargetEntity().get(Keys.TOTAL_EXPERIENCE).orElseGet(() -> 0) / 100) * 25;
                Entity experience = getloc.createEntity(EntityTypes.EXPERIENCE_ORB);
                try (CauseStackManager.StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
                    frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);
                    experience.offer(Keys.CONTAINED_EXPERIENCE, exp);
                    getloc.spawnEntity(experience);
                    player.offer(Keys.EXPERIENCE_LEVEL, 0);
                } catch (Exception e) {
                    System.out.println("Something went wrong... :(");
                    return;
                }

            }

        } else {
            return;

        }
    }
}







