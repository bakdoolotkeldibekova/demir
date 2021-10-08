package practice.demirbank.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import practice.demirbank.entity.Role;
import practice.demirbank.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
//    @Cacheable(cacheNames=CacheConstants.USER_CACHE,key="#username") // We cache this  guy by its username.
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        practice.demirbank.entity.User user = userRepository.findByLogin(login);
        List<Role> roles = new ArrayList<>();
        roles.add(user.getRole());
        return new User(user.getLogin(), user.getPassword(), roles);
    }

    //This is the cache used.
//    public class CacheConfig{
//        @Bean(name="localGauvaCaches")
//        public SimpleCacheManager localGuavaCaches(){
//            final SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
//            final GuavaCache userCache = new GuavaCache(CacheConstants.USER_CACHE, CacheBuilder.newBuilder()
//                    .concurrencyLevel(3) //Choose per your own will.
//                    .expireAfterAccess(2, TimeUnit.MINUTES) //Expire if not accessed within 2 minutes.
//                    .maximumSize(1000).build()); //For a maximum of 1000 User Objects.
//
//            simpleCacheManager.setCaches(Arrays.asList(userCache));
//
//            return simpleCacheManager;
//        }

    }