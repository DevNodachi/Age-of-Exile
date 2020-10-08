## v.1.5.2

- new system, item sealing. Modifying items adds instability to them, and increases chance they become sealed.
What this means for players is that at endgame you can't just dump thousands of currencies on 1 good item. 
For everyone else, this shouldn't matter at all.
- added crystal of purification, unseals the item.
- new reward for leveling. Mobs drop extra vanilla experience based on their level. (double at max level mobs)
- armor and hotbar will no longer be dropped on death by default. (SUPER EXPERIMENTAL, PROBABLY BUGGY) 
I think arpgs where it can take years to get good gear shouldn't allow you to lose it that easily.
Depending on how stable this feature is, it will either become core or removed.
- trying out .md changelogs
- updated chinese lang

## v1.5.1
- fix runewords not being given unless item had an empty socket

# v.1.5.0

- added new rpg like gui overlay! Thanks SattesKrokodil for making the gui parts! 
Set your PLAYER_GUI_TYPE in client configs to RPG.
It is now the default gui.

- level per distance now uses cordinate scale. So when you enter nether at lvl 5 in overworld, nether mobs should also be lvl 5. 
(but nether has minimum lvl by default so might be higher. but at least won't be max level like before)
In short, you won't meet lvl 50 mobs when you enter nether from lvl 1 locaton. 
But you will progress MUCH faster in mob lvls while walking in nether! 
This will work with all modded dimensions that use the vanilla cordinate scale value

- spell modifier datapacks removed. I realized i can just turn them into stats, that allows for more customization!
- caster has spellmod condition is now caster has stat
- added new datapack stats that can do everything spell modifiers did: cooldown, mana cost, damage etc (per spell)
- changed unique gear datapacks to not use item id for their lang name and desc keys. 
Now follows same naming theme as my other datapacks: modid.type.identifier
- added armor tag to plate armors. Fixes error in plate armor generation (nearly all affixes require armor tag)
- added extra projectiles to spell datapack stat (unused currently, open to modpackers)
- added istargetally condition for spells
- internal code change, statdata split into In Calculation data, and calculated data. Should prevent future bugs as now it's clear what context is given.
But it probably means short term bugs.
- datapack validation now runs after all datapacks are loaded, instead of on server start, this means invalid entries will no longer be shown on client
- "any weapon" spell cast requirement now allows any item to cast the spell. Including non age of exile weapons
- added remove negative potion effect spell action
- fix "target has effect" condition. 