package uspace.api.cruise.booking;

import uspace.application.cruise.booking.dtos.newZeroGravityExperienceBooking.NewZeroGravityExperienceBookingDto;
import uspace.domain.exceptions.MissingParameterException;

public class NewZeroGravityExperienceBookingDtoValidator {
    private static final String EXPERIENCE_BOOKING_DATE_TIME_PARAMETER = "experienceBookingDateTime";

    public void validate(NewZeroGravityExperienceBookingDto newZeroGravityExperienceBookingDto) {
        if (isExperienceBookingDateTimeMissing(newZeroGravityExperienceBookingDto)) {
            throw new MissingParameterException(EXPERIENCE_BOOKING_DATE_TIME_PARAMETER);
        }
    }

    private boolean isExperienceBookingDateTimeMissing(
            NewZeroGravityExperienceBookingDto newZeroGravityExperienceBookingDto) {
        return newZeroGravityExperienceBookingDto == null
               || newZeroGravityExperienceBookingDto.experienceBookingDateTime == null;
    }
}
