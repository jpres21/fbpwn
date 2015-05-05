A cross-platform Java based Facebook social engineering framework, sends friend requests to a list of Facebook profiles, and polls for the acceptance notification. Once the victim accepts the invitation, it dumps all their information,photos and friend list to a local folder. Extensible module interfaces and built-in modules for advanced social engineering tricks.


# Usage #

A typical hacking scenario starts with gathering information from a user's FB profile. The plugins are just a series of normal operations on FB, automated to increase the chance of you getting the info.

Typically, first you create a new blank account for the purpose of the test. Then, the friending plugin works first, by adding all the friends of the victim (to have some common friends). Then the clonning plugin asks you to choose one of the victims friends. The cloning plugin clones only the display picture and the display name of the chosen friend of victim and set it to the authenticated account. Afterwards, a friend request is sent to the victim's account. The dumper polls waiting for the friend to accept. As soon as the victim accepts the friend request, the dumper starts to save all accessable HTML pages (info, images, tags, ...etc) for offline examining.

After a a few minutes, probably the victim will unfriend the fake account after he/she figures out it's a fake, but probably it's too late!

# Disclaimer #

This project is a proof of concept (PoC) to make the world aware of the social engineering techniques used in the underworld. Use it on your own risk and please do not abuse!

Read [here](http://fbpwn.tumblr.com/post/10171402996/fbpwn-release-motivation) about the project release motivation.

# Project Team #

**Owner:**
  * **Saafan** is a senior information security analyst and the technical team lead of Raya IT Security Services Team (RISST). He is the founder of RISST’s application security division, specialized in software security and advanced penetration testing.

**Core Developers**
  * [Hussein El Motayam](http://about.me/HusseinElMotayam)
  * Ahmed El Shafiea
  * Mohamed Mansour

# Find us online #
[Twitter](http://twitter.com/_fbpwn)

[Tumblr](http://fbpwn.tumblr.com/)