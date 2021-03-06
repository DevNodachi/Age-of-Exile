package com.robertx22.age_of_exile.database.data.spell_schools.parser;

import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;

import java.util.*;

public class TalentGrid {

    SpellSchool school;

    List<List<GridPoint>> grid = new ArrayList<>();

    public GridPoint get(int x, int y) {
        return grid.get(x)
            .get(y);
    }

    public TalentGrid(SpellSchool school, String str) {
        this.school = school;

        int y = 0;
        for (String line : str.split("\n")) {
            int x = 0;
            for (String s : line.split(",")) {

                if (grid.size() <= x) {
                    grid.add(new ArrayList<>());
                }

                grid.get(x)
                    .add(new GridPoint(x, y, s));

                x++;

            }

            y++;
        }
    }

    public void loadIntoTree() {

        Set<GridPoint> perks = new HashSet<>();

        for (List<GridPoint> list : grid) {
            for (GridPoint point : list) {
                if (point.isTalent) {

                    try {
                        String perk = point.getPerk();
                        school.calcData.addPerk(point.getPoint(), perk);
                        perks.add(point);

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("PERK: " + point.getId() + " is broken!");
                    }

                } else if (point.isCenter()) {
                    school.calcData.center = point.getPoint();
                }
            }

        }

        Objects.requireNonNull(school.calcData.center, "Tree needs a center!");

        //  Watch conwatch = new Watch();
        // this can take a long time if the "hasPath" checks aren't minimized
        for (GridPoint one : perks) {
            for (GridPoint two : perks) {
                if (one.isInDistanceOf(two)) {
                    if (hasPath(one, two)) {
                        this.school.calcData.addConnection(one.getPoint(), two.getPoint());
                    }
                }
            }
        }
        // conwatch.print(" connecting tree");

    }

    // this is very resource intensive
    private boolean hasPath(GridPoint start, GridPoint end) {
        Queue<GridPoint> openSet = new ArrayDeque<>();
        openSet.add(start);

        Set<GridPoint> closedSet = new HashSet<>();

        while (!openSet.isEmpty()) {
            GridPoint current = openSet.poll();

            if (current.equals(end)) {
                return true;
            }

            if (!closedSet.add(current)) {
                continue; // we already visited it
            }

            if (current.isTalent && current != start) {
                continue; // skip exploring this path
            }

            openSet.addAll(getEligibleSurroundingPoints(current));
        }

        return false;
    }

    public Set<GridPoint> getEligibleSurroundingPoints(GridPoint p) {

        Set<GridPoint> set = new HashSet<>();
        int x = p.x;
        int y = p.y;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                if (isInRange(x + dx, y + dy)) {
                    GridPoint point = get(x + dx, y + dy);

                    if (Math.abs(dx) == 1 && Math.abs(dy) == 1) { // we are discovering a diagonal
                        if (get(x + dx, y).isTalent || get(x, y + dy).isTalent) {
                            continue; // skip this diagonal, it crosses a talent
                        }
                    }

                    if (point.isTalent || point.isConnector) {
                        set.add(point);
                    }
                }
            }
        }

        return set;
    }

    public boolean isInRange(int x, int y) {

        if (y < 2) {
            return false;
        }

        try {
            return get(x, y) != null;
        } catch (Exception e) {
        }

        return false;
    }

}