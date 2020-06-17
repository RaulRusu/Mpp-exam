package ro.ubb.monitor.web.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.monitor.web.dto.VoteDto;

@RestController
public class VotingController {

    @RequestMapping(value = "/voting", method = RequestMethod.POST)
    VoteDto addVotes(@RequestBody VoteDto voteDto) {
        System.out.println(voteDto);
        return voteDto;
    }
}
