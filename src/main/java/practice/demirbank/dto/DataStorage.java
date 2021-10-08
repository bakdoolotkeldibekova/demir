package practice.demirbank.dto;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class DataStorage {
     public HashMap<String, List<Long>> blockList = new HashMap<>();
}
