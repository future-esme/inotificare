package utm.md.service;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.md.domain.ChannelUserCredentials;
import utm.md.repository.ChannelUserCredentialsRepository;

/**
 * Service Implementation for managing {@link utm.md.domain.ChannelUserCredentials}.
 */
@Service
@Transactional
public class ChannelUserCredentialsService {

    private final Logger log = LoggerFactory.getLogger(ChannelUserCredentialsService.class);

    private final ChannelUserCredentialsRepository channelUserCredentialsRepository;

    public ChannelUserCredentialsService(ChannelUserCredentialsRepository channelUserCredentialsRepository) {
        this.channelUserCredentialsRepository = channelUserCredentialsRepository;
    }

    /**
     * Save a channelUserCredentials.
     *
     * @param channelUserCredentials the entity to save.
     * @return the persisted entity.
     */
    public ChannelUserCredentials save(ChannelUserCredentials channelUserCredentials) {
        log.debug("Request to save ChannelUserCredentials : {}", channelUserCredentials);
        return channelUserCredentialsRepository.save(channelUserCredentials);
    }

    /**
     * Update a channelUserCredentials.
     *
     * @param channelUserCredentials the entity to save.
     * @return the persisted entity.
     */
    public ChannelUserCredentials update(ChannelUserCredentials channelUserCredentials) {
        log.debug("Request to update ChannelUserCredentials : {}", channelUserCredentials);
        return channelUserCredentialsRepository.save(channelUserCredentials);
    }

    /**
     * Partially update a channelUserCredentials.
     *
     * @param channelUserCredentials the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChannelUserCredentials> partialUpdate(ChannelUserCredentials channelUserCredentials) {
        log.debug("Request to partially update ChannelUserCredentials : {}", channelUserCredentials);

        return channelUserCredentialsRepository
            .findById(channelUserCredentials.getId())
            .map(existingChannelUserCredentials -> {
                if (channelUserCredentials.getChatId() != null) {
                    existingChannelUserCredentials.setChatId(channelUserCredentials.getChatId());
                }
                if (channelUserCredentials.getChannel() != null) {
                    existingChannelUserCredentials.setChannel(channelUserCredentials.getChannel());
                }

                return existingChannelUserCredentials;
            })
            .map(channelUserCredentialsRepository::save);
    }

    /**
     * Get all the channelUserCredentials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChannelUserCredentials> findAll(Pageable pageable) {
        log.debug("Request to get all ChannelUserCredentials");
        return channelUserCredentialsRepository.findAll(pageable);
    }

    /**
     * Get one channelUserCredentials by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChannelUserCredentials> findOne(UUID id) {
        log.debug("Request to get ChannelUserCredentials : {}", id);
        return channelUserCredentialsRepository.findById(id);
    }

    /**
     * Delete the channelUserCredentials by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete ChannelUserCredentials : {}", id);
        channelUserCredentialsRepository.deleteById(id);
    }
}
