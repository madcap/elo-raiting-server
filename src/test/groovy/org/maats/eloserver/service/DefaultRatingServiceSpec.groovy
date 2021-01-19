package org.maats.eloserver.service

import org.maats.eloserver.data.MatchEntityRepository
import org.maats.eloserver.data.PlayerEntityRepository
import spock.lang.Specification

class DefaultRatingServiceSpec extends Specification {


    private EloService mockEloService = Mock()
    private MatchEntityRepository mockMatchEntityRepository = Mock()
    private PlayerEntityRepository mockPlayerEntityRepository = Mock()

    private DefaultRatingService service = new DefaultRatingService(
            eloService: mockEloService,
            matchEntityRepository: mockMatchEntityRepository,
            playerEntityRepository: mockPlayerEntityRepository,
    )

    // TODO JM

}
