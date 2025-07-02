package org.mrpaulwoods.sec12.assignment;

import org.mrpaulwoods.common.Util;

public class Lec08SlackAssignment {

    public static void main(String[] args) {

        // slack room
        var room = new SlackRoom("reactor");

        // create members
        var sam = new SlackMember("sam");
        var jake = new SlackMember("jake");
        var mike = new SlackMember("mike");

        // add two members
        room.addMember(sam);
        room.addMember(jake);

        sam.says("Hi All...");

        Util.sleepSeconds(4);

        jake.says("Hey!");
        sam.says("I simply wanted to say hi..");

        Util.sleepSeconds(4);

        room.addMember(mike);

        mike.says("Hey guys. glad to be here...");
    }
}
