package tw.waterballsa.gaas.unoflip.domain;

import tw.waterballsa.gaas.unoflip.vo.PlayerInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UnoFlipGame {
    private final int tableId;
    private final AtomicInteger positions = new AtomicInteger(1);
    private final List<PlayerInfo> playerInfoList = new ArrayList<>();

    public UnoFlipGame(int tableId) {
        this.tableId = tableId;
    }

    public void join(String playerId, String playerName) {
        int availablePosition = getAvailablePosition();
        playerInfoList.add(new PlayerInfo(playerId, playerName, availablePosition));
    }

    private int getAvailablePosition() {
        return positions.incrementAndGet();
    }

    public List<PlayerInfo> getPlayerInfoList() {
        return Collections.unmodifiableList(playerInfoList);
    }

    public int getPosition(String playerId) {
        return playerInfoList.stream()
                .filter(playerInfo -> playerId.equals(playerInfo.playerId()))
                .findFirst()
                .map(PlayerInfo::position)
                .orElseThrow(()-> new RuntimeException("player " + playerId + " is not in game"));
    }

    public int getTableId() {
        return tableId;
    }
}
