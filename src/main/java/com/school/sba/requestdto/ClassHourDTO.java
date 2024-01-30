package com.school.sba.requestdto;

import java.time.LocalDateTime;

import com.school.sba.enums.ClassStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassHourDTO {
	
	private int subjectId;
	private int classHourId;
	private int userId;
	private int roomNo;
	private LocalDateTime beginsAt;
    private LocalDateTime endsAt;
    private ClassStatus  classStatus;
 
}
