package ua.dargunovskiy.util;

import lombok.experimental.UtilityClass;
import ua.dargunovskiy.dto.ParticipantDto;
import ua.dargunovskiy.entity.Participant;

@UtilityClass
public class ParticipantDtoUtil {
    public ParticipantDto fromParticipantToParticipantDto(Participant participant) {
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setName(participant.getUser().getFirstName());
        participantDto.setSpeciality(participant.getUser().getSpeciality());
        return participantDto;
    }
}
