import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.sql.Time;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Commands extends ListenerAdapter {
    public Commands() throws LoginException, InterruptedException {
    }

    public static GuildMessageReceivedEvent event;
    String storedUser = "unkown";
    public static ArrayList commandchannel = new ArrayList<>(1);

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        commandchannel.add(main.bot.getTextChannelById("967925153407307809"));


        if (!event.getMember().getId().equalsIgnoreCase("878434802049642537") && commandchannel.contains(event.getChannel())) {
            System.out.println("got event");
            this.event = event;
            Dotenv dotenv = Dotenv.load();
            String systemPath = dotenv.get("SOURCE_DIRECTORY");
            String[] args = event.getMessage().getContentRaw().split("\\s+");
            //Text Channel IDs


            //Roles


            //Booleans


            if (args[0].equalsIgnoreCase("!commands")) {
                event.getChannel().sendTyping();
                event.getChannel().sendMessage("Check the list-of-commands channel").queue();
            }

            if(false) {
                for (int i = 0; i < 100; i++) {
                    int amountToChangeRolljump = (int) (Variables.defaultRJ * (getRandomPercentage(10, 30) / 100));
                    // System.out.println(amountToChangeRolljump);
                    if(Variables.currentRJ>50){
                        Variables.currentRJ = Variables.defaultRJ;
                    }
                    else{
                        Variables.currentRJ = Variables.currentRJ + amountToChangeRolljump;
                    }

                    System.out.println(Variables.currentRJ);

                }
            }
            if (args[0].equalsIgnoreCase("!rolljump+") || args[0].equalsIgnoreCase("!rj+")) {
                int amountToChangeRolljump = (int) (Variables.defaultRJ * (getRandomPercentage(10, 65) / 100));

                if(Variables.currentRJ>50){
                    Variables.currentRJ = Variables.defaultRJ + amountToChangeRolljump;
                }
                else{
                    Variables.currentRJ = Variables.currentRJ + amountToChangeRolljump;
                }

                main.runCommand("(set! (-> *TARGET-bank* wheel-flip-dist) (meters " + Variables.currentRJ + "))");

            }
            if (args[0].equalsIgnoreCase("!rolljump-") || args[0].equalsIgnoreCase("!rj-")) {
                int amountToChangeRolljump = (int) (Variables.defaultRJ * (getRandomPercentage(10, 95) / 100));

                if(Variables.currentRJ<-30){
                    System.out.println("resetting " + Variables.currentRJ);
                    Variables.currentRJ = Variables.defaultRJ - amountToChangeRolljump;
                }
                else{
                    Variables.currentRJ = Variables.currentRJ - amountToChangeRolljump;
                }

                main.runCommand("(set! (-> *TARGET-bank* wheel-flip-dist) (meters " + Variables.currentRJ + "))");

            }
            if (args[0].equalsIgnoreCase("!tripevery10seconds")) {
                while(true) {
                    main.runCommand("(send-event *target* 'loading)");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }

            if (args[0].equalsIgnoreCase("!rjto")) {
                main.runCommand("(set! (-> *TARGET-bank* wheel-flip-dist) (meters " + args[1] + "))");
                main.runCommand(changeRollJump((Integer.valueOf(args[1]))));
                Variables.currentRJ = Integer.valueOf(args[1]);
            }

            if (args[0].equalsIgnoreCase(("!superjump"))) {
                main.runCommand("(if (= (-> *TARGET-bank* jump-height-max)(meters 15.0))(begin (set! (-> *TARGET-bank* jump-height-max)(meters 3.5))(set! (-> *TARGET-bank* jump-height-min)(meters 1.01))(set! (-> *TARGET-bank* double-jump-height-max)(meters 2.5))(set! (-> *TARGET-bank* double-jump-height-min)(meters 1)))(begin (set! (-> *TARGET-bank* jump-height-max)(meters 15.0))(set! (-> *TARGET-bank* jump-height-min)(meters 5.0))(set! (-> *TARGET-bank* double-jump-height-max)(meters 15.0))(set! (-> *TARGET-bank* double-jump-height-min)(meters 5.0))))");
            }
            if (args[0].equalsIgnoreCase(("!normaljump"))) {
                main.runCommand(changeJumpPowerMin(1));
                main.runCommand(changeJumpPowerMax(3));
            }

            if (args[0].equalsIgnoreCase("!gotolevel") || args[0].equalsIgnoreCase("!gotopoint")) {
                main.runCommand("(start 'play (get-continue-by-name *game-info* \""+args[1]+"\")) ");
            }
            if (args[0].equalsIgnoreCase(("!movetojak"))) {
                main.runCommand("(when (process-by-ename \""+args[1]+"\")(set-vector!  (-> (-> (the process-drawable (process-by-ename \""+args[1]+"\"))root)trans) (-> (target-pos 0) x) (-> (target-pos 0) y) (-> (target-pos 0) z) 1.0))");
            }
            if (args[0].equalsIgnoreCase(("!eco"))) {
                main.runCommand("(send-event *target* 'get-pickup (pickup-type eco-"+args[1]+") 5.0)");

            }
            if (args[0].equalsIgnoreCase(("!superboosted"))) {
                main.runCommand("(if (not(=(-> *edge-surface* fric) 1.0))(set! (-> *edge-surface* fric) 1.0)(set! (-> *edge-surface* fric) 30720.0))");
            }
            if (args[0].equalsIgnoreCase(("!noboosteds"))) {
                main.runCommand("(if (not(=(-> *edge-surface* fric) 1530000.0))(set! (-> *edge-surface* fric) 1530000.0)(set! (-> *edge-surface* fric) 30720.0))");
            }
            if (args[0].equalsIgnoreCase(("!smallnet"))) {
                main.runCommand("(if (=(-> *FISHER-bank* net-radius)(meters 0.0))(set!(-> *FISHER-bank* net-radius)(meters 0.7))(set! (-> *FISHER-bank* net-radius)(meters 0.0)))");
            }
            if (args[0].equalsIgnoreCase(("!widefish"))) {
                main.runCommand("(if (=(-> *FISHER-bank* width)(meters 10.0))(set! (-> *FISHER-bank* width)(meters 3.3))(set! (-> *FISHER-bank* width)(meters 10.0)))");
            }
            if (args[0].equalsIgnoreCase(("!melt"))) {
                main.runCommand("(target-attack-up *target* 'attack 'melt)");
            }
            if (args[0].equalsIgnoreCase(("!endlessfall"))) {
                main.runCommand("(target-attack-up *target* 'attack 'endlessfall)");
            }
            if (args[0].equalsIgnoreCase(("!burn"))) {
                main.runCommand("(target-attack-up *target* 'attack 'burnup)");
            }
            if (args[0].equalsIgnoreCase(("!give"))) {
                main.runCommand("(set! (-> *game-info* "+args[1]+") (+ (-> *game-info* "+args[1]+") "+args[2]+"))");
            }
            if (args[0].equalsIgnoreCase(("!setcollected"))) {
                main.runCommand("(set! (-> *game-info* "+args[1]+") "+args[2]+")");
            }
            if (args[0].equalsIgnoreCase(("!enemyspeed"))) {
                main.runCommand("(set! (-> *"+args[1]+"-nav-enemy-info* run-travel-speed) (meters "+args[2]+"))");
            }

            if (args[0].equalsIgnoreCase(("!moveplantboss"))) {
                main.runCommand("(set! (-> *pc-settings* force-actors?) #t)");
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                main.runCommand("(when (process-by-ename \"plant-boss-3\")(set-vector!  (-> (-> (the process-drawable (process-by-ename \"plant-boss-3\"))root)trans) (meters 436.97) (meters -43.99) (meters -347.09) 1.0))");
                main.runCommand("(set! (-> (the-as fact-info-target (-> *target* fact))health) 1.0)");
                //main.runCommand(" (start 'play (get-or-create-continue! *game-info*))");
                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                main.runCommand("(set! (-> (target-pos 0) x) (meters 431.47))  (set! (-> (target-pos 0) y) (meters -44.00)) (set! (-> (target-pos 0) z) (meters -334.09))");
            }
            if (args[0].equalsIgnoreCase(("!moveplantboss2"))) {
                main.runCommand("(set! (-> *pc-settings* force-actors?) #t)");
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                main.runCommand("(when (process-by-ename \"plant-boss-3\")(set-vector!  (-> (-> (the process-drawable (process-by-ename \"plant-boss-3\"))root)trans) (meters 436.97) (meters -43.99) (meters -347.09) 1.0))");
            }
            if (args[0].equalsIgnoreCase("!tp")) {
                main.runCommand("(set! (-> (target-pos 0) x) (meters "+args[1]+"))  (set! (-> (target-pos 0) y) (meters "+ args[2]+")) (set! (-> (target-pos 0) z) (meters "+args[3]+"))");
            }
            if (args[0].equalsIgnoreCase("!shift")) {
                main.runCommand("(set! (-> (target-pos 0) x) (+ (-> (target-pos 0) x)(meters "+args[1]+")))  (set! (-> (target-pos 0) y) (+ (-> (target-pos 0) y)(meters "+args[2]+"))) (set! (-> (target-pos 0) z) (+ (-> (target-pos 0) z)(meters "+args[3]+")))");
            }

            if (args[0].equalsIgnoreCase(("!nopunching"))) {

                main.runCommand("(set! (-> *FACT-bank* eco-full-timeout) (seconds 20 ))");

                main.runCommand("(pc-cheat-toggle-and-tune *pc-settings* eco-yellow)");
            }
            if(args[0].equalsIgnoreCase("!deload")){
                main.runCommand("(set! (-> *load-state* want 0 display?) #f)");
            }
            if(args[0].equalsIgnoreCase("!loadlevel")){
                main.runCommand("(set! (-> *load-state* want 1 name) '"+args[1]+")(set! (-> *load-state* want 1 display?) 'display)");
            }
            if (args[0].equalsIgnoreCase(("!noeco"))) {
                main.runCommand("(send-event *target* 'get-pickup (pickup-type eco-yellow) 0.1)");
                main.runCommand("(send-event *target* 'get-pickup (pickup-type eco-red) 0.1)");
                main.runCommand("(set! (-> *FACT-bank* eco-full-timeout) (seconds 0.0001 ))");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                main.runCommand("(set! (-> *FACT-bank* eco-full-timeout) (seconds "+Variables.defaultEcoTime+" ))");
            }

            if (args[0].equalsIgnoreCase(("!randomcheckpoint"))) {

                String randomCheckpoint = (String) Variables.allCheckpoints.get((int) getRandomPercentage(0,Variables.allCheckpoints.size()));
                while (randomCheckpoint.equalsIgnoreCase("")){
                    randomCheckpoint = (String) Variables.allCheckpoints.get((int) getRandomPercentage(0,Variables.allCheckpoints.size()));
                }
                main.runCommand("(start 'play (get-continue-by-name *game-info* \""+randomCheckpoint+"\"))");
            }


            if (args[0].equalsIgnoreCase(("!sucksuck"))) {
                //todo add suck-suck default to message

                main.runCommand("(set! (-> *FACT-bank* suck-suck-dist) (meters " + args[1] + "))");
                main.runCommand("(set! (-> *FACT-bank* suck-bounce-dist) (meters " + args[1] + "))");
            }
            if (args[0].equalsIgnoreCase(("!getoff"))) {
                main.runCommand("(send-event *target* 'end-mode)");
            }
            if (args[0].equalsIgnoreCase(("!trip"))) {
                main.runCommand("(send-event *target* 'loading)");
            }

            if (args[0].equalsIgnoreCase(("!dax"))) {
                main.runCommand("(send-event *target* 'sidekick (not (not (send-event *target* 'sidekick #t))))");

            }
            if (args[0].equalsIgnoreCase(("!ouch"))) {

                main.runCommand("(send-event *target* 'attack #t (new 'static 'attack-info)) ");
            }
            if (args[0].equalsIgnoreCase(("!lavawalk"))) {


                main.runCommand("(send-event *target* 'attack #t (new 'static 'attack-info)) ");
                main.runCommand("(send-event *target* 'edge-grab)");
            }
            //todo fix this

            if (args[0].equalsIgnoreCase("!lod")){
                main.runCommand("(if (= (-> *pc-settings* lod-force-tfrag) 2)(begin(set! (-> *pc-settings* lod-force-tfrag) 0)(set! (-> *pc-settings* lod-force-tie) 0)(set! (-> *pc-settings* lod-force-ocean) 0)(set! (-> *pc-settings* lod-force-actor) 0))(begin(set! (-> *pc-settings* lod-force-tfrag) 2)(set! (-> *pc-settings* lod-force-tie) 3)(set! (-> *pc-settings* lod-force-ocean) 2)(set! (-> *pc-settings* lod-force-actor) 3)))");
            }

            if (args[0].equalsIgnoreCase(("!dark"))) {
                main.runCommand("(if (not (= (-> (level-get-target-inside *level*) mood-func)update-mood-finalboss)) (set! (-> (level-get-target-inside *level*) mood-func)update-mood-finalboss) (set! (-> (level-get-target-inside *level*) mood-func)update-mood-training))");
            }
            if (args[0].equalsIgnoreCase(("!heal"))) {
                main.runCommand("(send-event *target* 'get-pickup 4 1.0)");
            }
            if (args[0].equalsIgnoreCase(("!setecotime"))) {
                main.runCommand("(set! (-> *FACT-bank* eco-full-timeout) (seconds " + args[1] + "))");
            }
            if (args[0].equalsIgnoreCase(("!setOrbInc"))) {
                main.runCommand("(set! (-> *CUSTOM-bank* orb-count-inc) (float " + args[1] + "))");
            }

            if (args[0].equalsIgnoreCase(("!setflutflut"))) {
                main.runCommand("(set! (-> *flut-walk-mods* target-speed)(meters " + args[1] + "))");
            }
            if (args[0].equalsIgnoreCase(("!fastjak"))) {
                main.runCommand("(if (not(=(-> *jump-attack-mods* target-speed) 99999.0))(begin(if (=(-> *walk-mods* target-speed) 20000.0)(pc-cheat-toggle-and-tune *pc-settings* eco-yellow))(set! (-> *walk-mods* target-speed) 99999.0)(set! (-> *double-jump-mods* target-speed) 99999.0)(set! (-> *jump-mods* target-speed) 99999.0)(set! (-> *jump-attack-mods* target-speed) 99999.0)(set! (-> *attack-mods* target-speed) 99999.0)(set! (-> *TARGET-bank* wheel-flip-dist) (meters 17)))(begin(set! (-> *walk-mods* target-speed) 40960.0)(set! (-> *double-jump-mods* target-speed) 32768.0)(set! (-> *jump-mods* target-speed) 40960.0)(set! (-> *jump-attack-mods* target-speed) 24576.0)(set! (-> *attack-mods* target-speed) 40960.0)(set! (-> *TARGET-bank* wheel-flip-dist) (meters 17))))");
            }
            if (args[0].equalsIgnoreCase(("!slowjak"))) {
                main.runCommand("(if (not(=(-> *jump-attack-mods* target-speed) 20000.0))(begin(set! (-> *walk-mods* target-speed) 20000.0)(set! (-> *double-jump-mods* target-speed) 20000.0)(set! (-> *jump-mods* target-speed) 20000.0)(set! (-> *jump-attack-mods* target-speed) 2000.0)(set! (-> *attack-mods* target-speed) 20000.0)(set! (-> *TARGET-bank* wheel-flip-dist) (meters 0)))(begin(set! (-> *walk-mods* target-speed) 40960.0)(set! (-> *double-jump-mods* target-speed) 32768.0)(set! (-> *jump-mods* target-speed) 40960.0)(set! (-> *jump-attack-mods* target-speed) 24576.0)(set! (-> *attack-mods* target-speed) 40960.0)(set! (-> *TARGET-bank* wheel-flip-dist) (meters 17))))(pc-cheat-toggle-and-tune *pc-settings* eco-yellow)");
            }
            if (args[0].equalsIgnoreCase(("!invertcam"))) {
                main.runCommand("(set! (-> *pc-settings* "+ args[1]+"-camera-"+ args[2]+"-inverted?) (not (-> *pc-settings* "+ args[1]+"-camera-"+ args[2]+"-inverted?)))");
            }
            if (args[0].equalsIgnoreCase(("!normalcam"))) {
                main.runCommand("(set! (-> *pc-settings* third-camera-h-inverted?) #t)(set! (-> *pc-settings* third-camera-v-inverted?) #t)(set! (-> *pc-settings* first-camera-v-inverted?) #t)(set! (-> *pc-settings* first-camera-h-inverted?) #f)");
            }
            if (args[0].equalsIgnoreCase(("!actorson"))) {
                main.runCommand("(set! (-> *pc-settings* force-actors?) #t)");
            }
            if (args[0].equalsIgnoreCase(("!actorsoff"))) {
                main.runCommand("(set! (-> *pc-settings* force-actors?) #f)");
            }
            if (args[0].equalsIgnoreCase(("!debug"))) {
                main.runCommand("(set! *debug-segment* (not *debug-segment*))");
                main.runCommand("(set! *cheat-mode* (not *cheat-mode*))");
            }
            if (args[0].equalsIgnoreCase(("!shortfall"))) {
                main.runCommand("(if (= (-> *TARGET-bank* fall-far) (meters 1))(begin(set! (-> *TARGET-bank* fall-far) (meters 30))(set! (-> *TARGET-bank* fall-far-inc) (meters 20)))(begin (set! (-> *TARGET-bank* fall-far) (meters 1))(set! (-> *TARGET-bank* fall-far-inc) (meters 1))))");

            }

            if (args[0].equalsIgnoreCase(("!basincell"))) {
                main.runCommand("(if (when (process-by-ename \"fuel-cell-45\") (= (-> (->(the process-drawable (process-by-ename \"fuel-cell-45\"))root)trans x)  (meters -266.54)))(when (process-by-ename \"fuel-cell-45\")(set-vector!  (-> (-> (the process-drawable (process-by-ename \"fuel-cell-45\"))root)trans) (meters -248.92) (meters 52.11) (meters -1515.66) 1.0))(when (process-by-ename \"fuel-cell-45\")(set-vector!  (-> (-> (the process-drawable (process-by-ename \"fuel-cell-45\"))root)trans) (meters -266.54) (meters 52.11) (meters -1508.48) 1.0)))");

            }


            if (args[0].equalsIgnoreCase(("!frickstorage"))) {
                main.runCommand(" (stop 'debug)");
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                main.runCommand(" (start 'debug (get-or-create-continue! *game-info*))");

            }



            if (args[0].equalsIgnoreCase("!freecam")) {
                main.runCommand(" (stop 'debug)");
                try {
                    TimeUnit.SECONDS.sleep(6);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                main.runCommand(" (start 'play (get-or-create-continue! *game-info*))");

            }
            if (args[0].equalsIgnoreCase("!die")) {
                main.runCommand("(initialize! *game-info* 'die (the-as game-save #f) (the-as string #f))");
            }
            //(defun addtoObjective ()
            //(set! *objective123* (+ *objective123* 1))
            //)


            if (args[0].equalsIgnoreCase("!snow")) {
                main.runCommand("(run-next-time-in-process (the process (activate (get-process *default-dead-pool* process #x4000) *default-pool* 'process (the pointer #x70004000))) (lambda () (while #t (if *target* (update-snow *target*)) (suspend))))");
            }
            if (args[0].equalsIgnoreCase("!rain")) {
                main.runCommand("(run-next-time-in-process (the process (activate (get-process *default-dead-pool* process #x4000) *default-pool* 'process (the pointer #x70004000))) (lambda () (while #t (if *target* (update-rain *target*)) (suspend))))");
            }
            System.out.println(event.getMember().getId());
            if (args[0].equalsIgnoreCase("!repl") &&
                    (event.getMember().getId().equalsIgnoreCase("178908133475876865") ||
                            (event.getMember().getId().equalsIgnoreCase("534921732608360449") ||
                                    (event.getMember().getId().equalsIgnoreCase("126398522361643008") ||
                                            (event.getMember().getId().equalsIgnoreCase("277309197798866946") ||
                                                    (event.getMember().getId().equalsIgnoreCase("140194315518345216"))))))) {
                String fullString = "";
                for (int i = 1; i < args.length; i++) {

                    fullString = fullString + args[i] + " ";

                }
                main.runCommand(fullString);
            }
            if (args[0].equalsIgnoreCase("!randomize")) {
                randomizeMovementRepl();
            }


        }
    }

    public static float getRandomPercentage(double min, double max) {

        // define the range

        int range = (int) (max - min + 1);
        int rand = 0;
        // generate random numbers within 1 to 10
        for (int i = 0; i < 10; i++) {
            rand = (int) ((int) (Math.random() * range) + min);


        }
        //todo debug

        return rand;
    }

    public static String changeJumpPowerMin(int args){
        return "(set! (-> *TARGET-bank* jump-height-min) (meters "+args+"))";
    }
    public static String changeJumpPowerMax(int args){
        return "(set! (-> *TARGET-bank* jump-height-max) (meters "+args+"))";
    }
    public static String changeDblJumpPowerMin(int args){
        return "(set! (-> *TARGET-bank* double-jump-height-min) (meters "+args+"))";
    }
    public static String changeDblJumpPowerMax(int args){
        return "(set! (-> *TARGET-bank* double-jump-height-max) (meters "+args+"))";
    }

    public static String changeRollJump(int args){
        return "(set! (-> *TARGET-bank* wheel-flip-dist) (meters "+args+"))";
    }
// fastflutflut (set! (-> *flut-walk-mods* target-speed)(meters 50.0))


    /**(define *TARGET-bank* (new 'static 'target-bank
     :jump-collide-offset (meters 0.7)
     :jump-height-min (meters 1.01)
     :jump-height-max (meters 3.5)
     :double-jump-height-min (meters 1)
     :double-jump-height-max (meters 2.5)
     :flip-jump-height-min (meters 5)
     :flip-jump-height-max (meters 7)
     :duck-jump-height-min (meters 5)
     :duck-jump-height-max (meters 7)
     :flop-jump-height-min (meters 5)
     :flop-jump-height-max (meters 7)
     :attack-jump-height-min (meters 4)
     :attack-jump-height-max (meters 5.5)
     :edge-grab-jump-height-min (meters 1.7)
     :edge-grab-jump-height-max (meters 1.7)
     :swim-jump-height-min (meters 5)
     :swim-jump-height-max (meters 5)
     :tube-jump-height-min (meters 1.75)
     :tube-jump-height-max (meters 2.5)
     :wheel-duration (seconds 0.5)
     :wheel-jump-pre-window (seconds 1)
     :wheel-jump-post-window (seconds 0.1)
     :wheel-speed-min (meters 11.5)
     :wheel-speed-inc (meters 1.5)
     :wheel-flip-duration (seconds 0.7)
     :wheel-flip-height (meters 3.52)
     :wheel-flip-dist (meters 17.3)
     :wheel-flip-art-height (meters 3.2969)
     :wheel-flip-art-dist (meters 12.5)
     :duck-slide-distance (meters 5.75)
     :fall-far (meters 30)
     :fall-far-inc (meters 20)
     :attack-timeout (seconds 0.3)
     :ground-timeout (seconds 0.2)
     :slide-down-timeout (seconds 0.2)
     :fall-timeout (seconds 1)
     :fall-stumble-threshold (meters 39.9)
     :yellow-projectile-speed (meters 60)
     :hit-invulnerable-timeout (seconds 3)
     :run-cycle-length 60.0
     :walk-cycle-dist (meters 2.8)
     :walk-up-cycle-dist (meters 2.8)
     :walk-down-cycle-dist (meters 2.8)
     :walk-side-cycle-dist (meters 2.8)
     :run-cycle-dist (meters 6.25)
     :run-up-cycle-dist (meters 5)
     :run-down-cycle-dist (meters 5)
     :run-side-cycle-dist (meters 6.25)
     :run-wall-cycle-dist (meters 2.8)
     :duck-walk-cycle-dist (meters 3.25)
     :wade-shallow-walk-cycle-dist (meters 6)
     :wade-deep-walk-cycle-dist (meters 6)
     :smack-surface-dist (meters 1.25)
     :min-dive-depth (meters 2)
     :root-radius (meters 2.2)
     :root-offset (new 'static 'vector :y 4915.2 :w 1.0)
     :body-radius (meters 0.7)
     :edge-radius (meters 0.35)
     :edge-offset
     (new 'static 'vector :y 4915.2 :z 4096.0 :w 1.0)
     :head-radius (meters 0.7)
     :head-height (meters 1.4)
     :head-offset (new 'static 'vector :y 4915.2 :w 1.0)
     :spin-radius (meters 2.2)
     :spin-offset (new 'static 'vector :y 6553.6 :w 1.0)
     :duck-spin-radius (meters 1.2)
     :duck-spin-offset (new 'static 'vector :y 4096.0 :w 1.0)
     :punch-radius (meters 1.3)
     :punch-offset (new 'static 'vector :y 5324.8 :w 1.0)
     :uppercut-radius (meters 1)
     :uppercut0-offset (new 'static 'vector :y 3276.8 :w 1.0)
     :uppercut1-offset (new 'static 'vector :y 9011.2 :w 1.0)
     :flop-radius (meters 1.4)
     :flop0-offset (new 'static 'vector :y 3276.8 :w 1.0)
     :flop1-offset (new 'static 'vector :y 9011.2 :w 1.0)
     :stuck-time (seconds 0.3)
     :stuck-timeout (seconds 2)
     :stuck-distance (meters 0.05)
     :tongue-pull-speed-min 0.15
     :tongue-pull-speed-max 0.22
     :yellow-attack-timeout (seconds 0.2)
     )**/











    public static void setJaktoDefault(){


    }

    public static void randomizeMovementRepl(){
        int min = -40;
        int max = 40;

        //Generate random int value from 50 to 100
        System.out.println("Random value in int from "+min+" to "+max+ ":");
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        System.out.println(random_int);

        main.runCommand("(set! (-> *TARGET-bank* wheel-flip-dist) (meters "+random_int+"))");

    }
}