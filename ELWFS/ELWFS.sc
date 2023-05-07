ELWFS {
	*ar{
		arg input, number_of_speakers = 8, speaker_distance=0.127, virutal_z=0.0, virutal_x=0.0, posHead=1, k = 0.1;
		var sig, speaker_dist, distance, time_delay, filter, positionHeadDist, inverse, max_delayTime, speedOfSound, amp;

		sig = input;

		max_delayTime = 0.3;
		speedOfSound = 343;

		speaker_dist=Array.series((number_of_speakers*0.5), (speaker_distance*0.5), speaker_distance);

		inverse = speaker_dist.reverse*(-1);

		speaker_dist = inverse ++ speaker_dist;

		distance=((virutal_x-speaker_dist).pow(2)) + ((virutal_z).pow(2)).sqrt;

		time_delay = (distance / speedOfSound).abs;


		positionHeadDist=((virutal_x-speaker_dist).pow(2)) + ((((-1)*posHead)-virutal_z).pow(2)).sqrt;


		time_delay=DelayN.ar(sig, 0.64, time_delay);

		filter = RLPF.ar(time_delay, (20000/(1 + (0.1 * positionHeadDist.abs))));

		amp = (1/(1 + (k * positionHeadDist.abs)));

		sig = filter *amp* 0.125;
		^sig;

	}

}








