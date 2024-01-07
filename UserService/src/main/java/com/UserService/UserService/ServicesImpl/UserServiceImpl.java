package com.UserService.UserService.ServicesImpl;

import com.UserService.UserService.Entity.*;
import com.UserService.UserService.EntityDTO.RoleDTO;
import com.UserService.UserService.EntityDTO.UserDTO;
import com.UserService.UserService.Repository.*;
import com.UserService.UserService.Services.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BenevoleRepository benevoleRepository;

    @Autowired
    private DemandeurRepository demandeurRepository;

    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private ValidatorDemandeurLinkRepository validatorDemandeurLinkRepository;

    @Override
    public Stream<UserDTO> getAllUsers() {
        return userRepository.findAllLoginAndId().stream().map(user -> modelMapper.map(user, UserDTO.class));}

    @Override
    public void putUser(User user) {

    }

    @Override
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void createNewUser(User user) {

        if (userRepository.findUserByLogin(user.getLogin()).isPresent()){
            logger.error("Login already used");
        }else {
            User new_user = new User();
            new_user.setLogin(user.getLogin());
            new_user.setPassword(user.getPassword());
            new_user.setIsBenevole(user.getIsBenevole());
            new_user.setIsValidator(user.getIsValidator());
            new_user.setIsDemandeur(user.getIsDemandeur());
            userRepository.save(new_user);
            if (user.getIsDemandeur()) {
                Demandeur demandeur = new Demandeur();
                demandeur.setDemandeur(user);
                demandeurRepository.save(demandeur);
            }
            if (user.getIsValidator()) {
                Validator validator= new Validator();
                validator.setValidator(user);
                validatorRepository.save(validator);
            }
            if (user.getIsBenevole()) {
                Benevole benevole = new Benevole();
                benevole.setBenevole(user);
                benevoleRepository.save(benevole);
            }
        }
    }

    @Override
    public Optional<User> getUserByLogin(String Login) {
        return userRepository.findUserByLogin(Login);
    }

    @Override
    public ResponseEntity<Object> deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()){
            logger.error("User not found !");
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }
        else {
            userRepository.deleteById(id);
            return new ResponseEntity<>(String.format("User %d removed",id),HttpStatus.OK);
        }

    }


    @Override
    @Transactional
    public Optional<User> updateUser(Long id, RoleDTO roleDTO) {
        logger.info("in userService : updateUser");
        Optional<User> updatedUser = getUserById(id);
        if (updatedUser.isEmpty()){
            logger.error("No user Found");
            return Optional.empty();
        }
        logger.info(roleDTO.toString());

        updatedUser.get().setIsDemandeur(roleDTO.getIsDemandeur());
        updatedUser.get().setIsValidator(roleDTO.getIsValidator());
        updatedUser.get().setIsBenevole(roleDTO.getIsBenevole());

        if (demandeurRepository.existsById(id)){
            logger.info("user exist in table demandeur");
            if(!updatedUser.get().getIsDemandeur()){
                logger.info("deleting user");
                demandeurRepository.deleteById(id);
            }
        }
        else{
            if(updatedUser.get().getIsDemandeur()){
                Demandeur new_demandeur = new Demandeur();
                new_demandeur.setId(updatedUser.get().getId());
                demandeurRepository.save(new_demandeur);
            }
        }
        if (benevoleRepository.existsById(id)){
            if(!updatedUser.get().getIsBenevole()){
                benevoleRepository.deleteById(id);
            }
        }
        else{
            if(updatedUser.get().getIsBenevole()){
                Benevole new_benevole = new Benevole();
                new_benevole.setId(updatedUser.get().getId());
                benevoleRepository.save(new_benevole);
            }
        }

        logger.info(updatedUser.toString());

        logger.info("user updated");
        return updatedUser;
    }

    @Override
    public Stream<UserDTO> getAllDemandeur() {
        return userRepository.findAllDemandeur().stream().map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public Stream<UserDTO> getAllBenevole() {
        return userRepository.findAllBenevole().stream().map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public Stream<UserDTO> getAllValidator() {
        return userRepository.findAllValidator().stream().map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    @Transactional
    public ResponseEntity<Object> linkValidatorToDemandeur(Long validatorId, Long demandeurId) {
        Optional<Validator> validatorOptional = validatorRepository.findById(validatorId);
        Optional<Demandeur> demandeurOptional = demandeurRepository.findById(demandeurId);

        if (validatorOptional.isEmpty()) {
            return new ResponseEntity<>("Validator not found", HttpStatus.NOT_FOUND);
        }

        if (demandeurOptional.isEmpty()) {
            return new ResponseEntity<>("Demandeur not found", HttpStatus.NOT_FOUND);
        }

        Validator validator = validatorOptional.get();
        Demandeur demandeur = demandeurOptional.get();

        if (validatorDemandeurLinkRepository.existsByValidatorAndDemandeur(validator, demandeur)) {
            return new ResponseEntity<>("Link already exists", HttpStatus.BAD_REQUEST);
        }

        ValidatorDemandeurLink link = new ValidatorDemandeurLink();
        link.setId(new ValidatorDemandeurLinkId(validatorId, demandeurId));
        link.setValidator(validator);
        link.setDemandeur(demandeur);


        validatorDemandeurLinkRepository.save(link);

        return new ResponseEntity<>(String.format("Validator with ID %d linked to Demandeur with ID %d", validatorId, demandeurId), HttpStatus.CREATED);
    }

    @Override
    public Boolean getLink(Long validatorId, Long demandeurId) {
        Optional<Validator> validatorOptional = validatorRepository.findById(validatorId);
        Optional<Demandeur> demandeurOptional = demandeurRepository.findById(demandeurId);

        if (validatorOptional.isEmpty()) {
            return Boolean.FALSE;
        }

        if (demandeurOptional.isEmpty()) {
            return Boolean.FALSE;
        }
        Validator validator = validatorOptional.get();
        Demandeur demandeur = demandeurOptional.get();

        return validatorDemandeurLinkRepository.existsByValidatorAndDemandeur(validator,demandeur);
    }
// Cette fonction renvoie true s'il existe un lien entre le Validator et le demandeur, renvoie aussi true
// la fonction renvoie aussi true si le lien est crée
    // la fonction retourne false si les on a pas un demandeur et un validator


    public boolean tryLinkValidatorToDemandeur(Long validatorId, Long demandeurId) {
        Optional<Validator> validatorOptional = validatorRepository.findById(validatorId);
        Optional<Demandeur> demandeurOptional = demandeurRepository.findById(demandeurId);

        if (validatorOptional.isEmpty() || demandeurOptional.isEmpty()) {
            return false;
        }

        Validator validator = validatorOptional.get();
        Demandeur demandeur = demandeurOptional.get();

        if (validatorDemandeurLinkRepository.existsByValidatorAndDemandeur(validator, demandeur)) {
            return true; // Le lien existe déjà
        }

        ValidatorDemandeurLink link = new ValidatorDemandeurLink();
        link.setId(new ValidatorDemandeurLinkId(validatorId, demandeurId));
        link.setValidator(validator);
        link.setDemandeur(demandeur);

        validatorDemandeurLinkRepository.save(link);
        return true; // Le lien a été créé avec succès
    }

    public Boolean isUserAValidator(Long userId) {
        return validatorRepository.existsById(userId);
    }
    @Override
    public Boolean isUserADemandeur(Long userId) {
        return demandeurRepository.existsById(userId);
    }

    @Override
    public Boolean isUserABenevole(Long userId) {
        return benevoleRepository.existsById(userId);
    }
}
